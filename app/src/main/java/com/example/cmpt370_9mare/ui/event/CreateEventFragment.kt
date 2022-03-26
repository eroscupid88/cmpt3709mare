package com.example.cmpt370_9mare.ui.event

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.FragmentCreateEventBinding
import com.example.cmpt370_9mare.ui.calendar.CalendarViewModel
import com.example.cmpt370_9mare.ui.calendar.CalendarViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


private const val TAG = "createEventFragment"

@RequiresApi(Build.VERSION_CODES.O)
class CreateEventFragment : Fragment() {
    private val navigationArgs: CreateEventFragmentArgs by navArgs()
    private var eventId = 0

    private lateinit var currentEvent: ScheduleEvent

    private val calendarViewModel: CalendarViewModel by activityViewModels {
        CalendarViewModelFactory(
            (activity?.application as ScheduleApplication).database.scheduleEventDao()
        )
    }

    /**
     * get Singleton scheduleEventViewModel shared throughout fragments
     */
    private val scheduleEventShareViewModel: ScheduleEventViewModel by activityViewModels {
        ScheduleEventViewModelFactory(
            (activity?.application as ScheduleApplication).database.scheduleEventDao()
        )
    }

    private val spinnerViewModel: SpinnerViewModel by activityViewModels()

    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!
    private val inputs
        get() = object {
            val title = binding.inputTitle.text.toString()
            val location = binding.inputLocation.text.toString()
            val date = binding.inputDate.text.toString()
            val timeFrom = binding.inputTimeFrom.text.toString()
            val timeTo = binding.inputTimeTo.text.toString()
            val url = binding.eventUrl.text.toString()
            val notes = binding.eventNotes.text.toString()
        }

    private var spinner: Spinner? = null

    /**
     * create CreateEventFragment instance and accept params argument from another fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get value of eventId argument
        eventId = navigationArgs.eventId
        // Clear the date and time variables in viewModel
        scheduleEventShareViewModel.pickDate(calendarViewModel.selectDate.value.toString())
        preloadTime()
    }

    /**
     * binding FragmentCreateEventBinding and inflate view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        spinner = binding.spRepeatEvery
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = scheduleEventShareViewModel
            spinnerViewModel = spinnerViewModel
            createEventFragment = this@CreateEventFragment
        }

        if (eventId > 0) {
            binding.apply {
                calendarTitle.text = getString(R.string.modify_event_title)
                submitCreateEvent.text = getString(R.string.update_button_text)
                deleteEvent.isVisible = true
            }
            scheduleEventShareViewModel.eventFromId(eventId)
                .observe(this.viewLifecycleOwner) { selectedItem ->
                    if (selectedItem != null) {
                        currentEvent = selectedItem
                        bind(currentEvent)
                    }
                }
        } else {
            spinnerViewModel.typeSelection.observe(viewLifecycleOwner) {
                binding.repeatDescription.text = getString(
                    R.string.repeat_description, when (it) {
                        1 -> "Day"
                        2 -> "Week"
                        else -> "Month"
                    }
                )
            }
        }

        setupListeners()
        setInputBinding()
    }

    override fun onResume() {
        super.onResume()
        scheduleEventShareViewModel.pickedDate.observe(
            this
        ) { binding.inputDate.text = it }
        scheduleEventShareViewModel.pickedTimeFrom.observe(
            this
        ) { binding.inputTimeFrom.text = it }
        scheduleEventShareViewModel.pickedTimeTo.observe(
            this
        ) { binding.inputTimeTo.text = it }
    }

    fun setSpinnerValue(position: Int) {
        spinnerViewModel.setSpinnerSelected(position)
        Log.d(TAG, "some: $position")
    }


    fun setRepeatEvery(position: Int) {
        when (position) {
            0 -> {

                ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.repeat_day_array,
                    android.R.layout.simple_spinner_dropdown_item
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    spinner!!.adapter = adapter
                }

                Log.i(TAG, "create drop down for daily")

            }
            1 -> {
                ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.repeat_week_array,
                    android.R.layout.simple_spinner_dropdown_item
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    spinner!!.adapter = adapter
                }.notifyDataSetChanged()
                Log.i(TAG, "create drop down for weekly")
            }

            2 -> {
                ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.repeat_month_array,
                    android.R.layout.simple_spinner_dropdown_item
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    spinner!!.adapter = adapter
                }
                Log.i(TAG, "create drop down for monthly")
            }
            else -> Log.i(TAG, "Hi just do nothing $position")
        }

        spinnerViewModel.setRepeatEvery(position)
        spinnerViewModel.setTypeSelected(position + 1)


    }

    /**
     * setInputBinding function to call handleKeyEvent if Enter button is clicked, it close down keyboard
     * better for user experience
     */
    private fun setInputBinding() {
        binding.inputTitle.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.inputLocation.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.eventUrl.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.eventNotes.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }

    }

    /**
     * Key listener for hiding the keyboard when the "Enter" button is tapped.
     */
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun addNewEvent() {
        var repeatType = 1
        var repeatLength: Long = 1
        spinnerViewModel.typeSelection.observe(viewLifecycleOwner) { repeatType = it }
        spinnerViewModel.lengthSelection.observe(viewLifecycleOwner) { repeatLength = it.toLong() }

        addEventWithAnotherDate(inputs.date)

        // Repeat if selected
        if (binding.repeatButton.isChecked) {
            for (x in (1..repeatLength)) {
                when (repeatType) {
                    1 -> {
                        addEventWithAnotherDate(
                            LocalDate.parse(inputs.date).plusDays(x).toString()
                        )
                    }
                    2 -> {
                        addEventWithAnotherDate(
                            LocalDate.parse(inputs.date).plusWeeks(x).toString()
                        )
                    }
                    else -> {
                        addEventWithAnotherDate(
                            LocalDate.parse(inputs.date).plusMonths(x).toString()
                        )
                    }
                }
            }
        }

        val action =
            CreateEventFragmentDirections.actionCreateEventFragmentToNavigationCalendar()
        findNavController().navigate(action)
    }

    private fun updateEvent() {
        currentEvent.apply {
            title = inputs.title
            location = inputs.location
            date = inputs.date
            time_from =
                if (binding.allDay.isChecked) getString(R.string.all_day) else inputs.timeFrom
            time_to = if (binding.allDay.isChecked) "" else inputs.timeTo
            url = inputs.url
            notes = inputs.notes
        }

        scheduleEventShareViewModel.updateItem(currentEvent)
        findNavController().navigateUp()
    }

    /**
     * Binds views with the passed in item data.
     */
    private fun bind(event: ScheduleEvent) {
        binding.apply {
            inputTitle.setText(event.title, TextView.BufferType.SPANNABLE)
            inputLocation.setText(event.location, TextView.BufferType.SPANNABLE)
            inputDate.text = event.date
            eventUrl.setText(event.url, TextView.BufferType.SPANNABLE)
            eventNotes.setText(event.notes, TextView.BufferType.SPANNABLE)

            if (event.time_from == getString(R.string.all_day)) {
                allDay.isChecked = true
                preloadTime()
            } else {
                allDay.isChecked = false
                inputTimeFrom.text = event.time_from
                inputTimeTo.text = event.time_to
            }

            pickTime.isVisible = !allDay.isChecked

            // No repeat option while updating events
            repeatButton.isChecked = false
            repeatButton.isVisible = false
        }
    }

    private fun addEventWithAnotherDate(anotherDate: String) {
        inputs.apply {
            scheduleEventShareViewModel.addNewItem(
                title,
                location,
                anotherDate,
                if (binding.allDay.isChecked) getString(R.string.all_day) else timeFrom,
                if (binding.allDay.isChecked) "" else timeTo,
                url,
                notes
            )
        }
    }

    fun cancelEvent() {
        Log.i(TAG, "$TAG: cancel Event button was clicked")
        findNavController().navigateUp()
    }

    fun createModifyEvent() {
        if (!binding.conflictCheck.isChecked) {
            if (eventId > 0) {
                updateEvent()
            } else {
                addNewEvent()
            }
        } else {
            inputs.apply {
                Log.d(TAG, "$TAG: $date, $timeFrom, $timeTo, $eventId")
                lifecycle.coroutineScope.launch {
                    scheduleEventShareViewModel.eventConflicts(date, timeFrom, timeTo, eventId)
                        .collect {
                            when {
                                it.isNotEmpty() -> {
                                    Log.i(TAG, "$TAG: Conflicts!")
                                    showConflictDialog(it)
                                }
                                eventId > 0 -> {
                                    Log.i(TAG, "$TAG: update Event button was clicked")
                                    updateEvent()
                                }
                                else -> {
                                    Log.i(TAG, "$TAG: add Event button was clicked")
                                    addNewEvent()
                                }
                            }
                        }
                }
            }
        }
    }

    fun showDatePicker() = DatePickerFragment(inputs.date).show(
        childFragmentManager,
        DatePickerFragment.DATE_PICKER
    )

    fun showTimeFromPicker() = TimePickerFragment(inputs.timeFrom).show(
        childFragmentManager,
        TimePickerFragment.TIME_FROM_PICKER
    )

    fun showTimeToPicker() = TimePickerFragment(inputs.timeTo).show(
        childFragmentManager,
        TimePickerFragment.TIME_TO_PICKER
    )

    // Used to build and create a pop-up menu detailing all conflict events
    // present from a database query
    private fun showConflictDialog(conflictEvents: List<ScheduleEvent>) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        builder.setTitle("Conflict Found")

        // Get all conflict events and format to string
        var txt = ""
        conflictEvents.forEach {
            txt += "${it.title}: ${it.time_from} - ${it.time_to}\n"
        }
        builder.setMessage(txt)

        builder.setNegativeButton("OK") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    /*
    * applying text watcher on each text field
    */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.input_title -> validateTitle()
                R.id.inputTimeFrom -> validateTimeInput()
                R.id.inputTimeTo -> validateTimeInput()
            }

            showSubmitButton(isValidated())
        }
    }

    private fun setupListeners() {
        binding.apply {
            inputTitle.addTextChangedListener(TextFieldValidation(inputTitle))
            inputTimeFrom.addTextChangedListener(TextFieldValidation(inputTimeFrom))
            inputTimeTo.addTextChangedListener(TextFieldValidation(inputTimeTo))

            // Listener for "All-Day" Toggle to show/hide time_picker
            allDay.setOnCheckedChangeListener { _, isCheck ->
                pickTime.isVisible = !isCheck
                // Do not check for conflict if "All-Day" is selected
                if (isCheck) {
                    conflictCheck.isChecked = false
                }
            }
            conflictCheck.setOnCheckedChangeListener { _, isCheck ->
                if (isCheck && eventId <= 0) {
                    repeatButton.isChecked = false
                }
            }
            repeatButton.setOnCheckedChangeListener { _, isCheck ->
                if (isCheck) {
                    conflictCheck.isChecked = false
                }
                repeatSpinners.isVisible = isCheck
            }
        }
    }


    private fun isValidated(): Boolean = validateTitle() && validateTimeInput()

    private fun validateTitle(): Boolean {
        return when {
            binding.inputTitle.text.toString().trim().isEmpty() -> {
                binding.eventTitle.error = "Required Title"
                binding.eventTitle.requestFocus()
                false
            }
            binding.inputTitle.text.toString().length > 30 -> {
                binding.eventTitle.error = "Title cannot exceeding 30 letters"
                false
            }
            else -> {
                binding.eventTitle.isErrorEnabled = false
                true
            }
        }
    }


    private fun validateTimeInput(): Boolean {
        return if (binding.inputTimeFrom.text.toString() != getString(R.string.all_day) && binding.inputTimeFrom.text.toString() != "" && binding.inputTimeTo.text.toString() != "") {
            if (LocalTime.parse(binding.inputTimeFrom.text.toString()) >= LocalTime.parse(binding.inputTimeTo.text.toString())) {
                binding.dateTimeLayout.error = "TimeTo must be later than TimeFrom"
                false
            } else {
                binding.dateTimeLayout.isErrorEnabled = false
                true
            }
        } else true
    }

    private fun preloadTime() {
        scheduleEventShareViewModel.pickTimeFrom(
            LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 5)
        )
        scheduleEventShareViewModel.pickTimeTo("23:59")
    }

    private fun showSubmitButton(boolean: Boolean) {
        binding.submitCreateEvent.visibility = if (boolean) View.VISIBLE else View.INVISIBLE
    }


    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage("Delete the current event?")
            .setCancelable(false)
            .setPositiveButton("Confirm") { _, _ ->
                scheduleEventShareViewModel.deleteItem(currentEvent)
                findNavController().navigateUp()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    /**
     * Delete Event
     */
    fun deleteEvent() {
        showDeleteConfirmationDialog()
    }

    /*
        setEventType function selection option of calendar type
     */
    fun setEventType(selection: Int) {
        spinnerViewModel.setEventTypeSelection(selection)
    }
}