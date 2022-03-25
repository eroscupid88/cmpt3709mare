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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val TAG = "createEventFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@RequiresApi(Build.VERSION_CODES.O)
class CreateEventFragment : Fragment() {
    private var param1: String? = null

    private val navigationArgs: CreateEventFragmentArgs by navArgs()
    private lateinit var currentEvent: ScheduleEvent

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

    private var spinner: Spinner? = null

    /**
     * create CreateEventFragment instance and accept params argument from another fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        // Clear the date and time variables in viewModel
        scheduleEventShareViewModel.pickDate(scheduleEventShareViewModel.today)
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
        spinner = _binding!!.spRepeatEvery
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

        val id = navigationArgs.eventId
        if (id > 0) {
            binding.apply {
                calendarTitle.text = getString(R.string.modify_event_title)
                submitCreateEvent.text = getString(R.string.update_button_text)
                deleteEvent.isVisible = true
            }
            scheduleEventShareViewModel.eventFromId(id)
                .observe(this.viewLifecycleOwner) { selectedItem ->
                    if (selectedItem != null) {
                        currentEvent = selectedItem
                        bind(currentEvent)
                    }
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
     * companion object is Singleton object pass to Fragment and ViewModel
     */
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param repeat Parameter 1.
         * @return A new instance of fragment CreateEventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(repeat: String) =
            CreateEventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, repeat)
                }
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


    private fun isEntryValid(): Boolean {
        return scheduleEventShareViewModel.isEntryValid(
            binding.inputTitle.text.toString()
        )
    }

    private fun updateEvent() {
        val isAllDayChecked = binding.allDay.isChecked

        if (isEntryValid()) {
            currentEvent.apply {
                title = binding.inputTitle.text.toString()
                location = binding.inputLocation.text.toString()
                date = binding.inputDate.text.toString()
                time_from =
                    if (isAllDayChecked) "all-day" else binding.inputTimeFrom.text.toString()
                time_to = if (isAllDayChecked) "" else binding.inputTimeTo.text.toString()
                url = binding.eventUrl.text.toString()
                notes = binding.eventNotes.text.toString()
            }
            scheduleEventShareViewModel.updateItem(currentEvent)
            findNavController().navigateUp()
        }
    }

    fun addNewEvent() {
        val repeatDate = binding.inputDate.text.toString()
        if (isEntryValid()) {
            //TODO: Create Events for all repeat cases up to 1 year.
            /*val test = arguments?.getString(ARG_REPEAT)
            Log.i(TAG, "$TAG: $test")
            when (test) {

            }*/

            val isAllDayChecked = binding.allDay.isChecked
            scheduleEventShareViewModel.addNewItem(
                binding.inputTitle.text.toString(),
                binding.inputLocation.text.toString(),
                repeatDate,
                if (isAllDayChecked) "all-day" else binding.inputTimeFrom.text.toString(),
                if (isAllDayChecked) "" else binding.inputTimeTo.text.toString(),
                binding.eventUrl.text.toString(),
                binding.eventNotes.text.toString()
            )

            // repeat event function
            repeatEvent(repeatDate, isAllDayChecked)

            val action =
                CreateEventFragmentDirections.actionCreateEventFragmentToNavigationCalendar()
            findNavController().navigate(action)

        }

    }

    private fun repeatEvent(date: String, isAllDayChecked: Boolean) {
        spinnerViewModel.typeSelection.observe(this.viewLifecycleOwner) {
            val option = it
            Log.i(TAG, "option :$option")
            spinnerViewModel.lengthSelection.observe(this.viewLifecycleOwner) {
                when (option) {
                    1 -> {
                        Log.i(TAG, "it :$it")
                        for (x: Int in (1..it)) {
                            Log.i(TAG, " x : $x")
                            scheduleEventShareViewModel.addNewItem(
                                binding.inputTitle.text.toString(),
                                binding.inputLocation.text.toString(),
                                LocalDate.parse(date).plusDays(x.toLong()).toString(),
                                if (isAllDayChecked) "all-day" else binding.inputTimeFrom.text.toString(),
                                if (isAllDayChecked) "" else binding.inputTimeTo.text.toString(),
                                binding.eventUrl.text.toString(),
                                binding.eventNotes.text.toString()
                            )
                        }
                    }
                    2 -> {
                        Log.i(TAG, "it :$it")
                        for (x: Int in (1..it)) {
                            Log.i(TAG, " x : $x")
                            scheduleEventShareViewModel.addNewItem(
                                binding.inputTitle.text.toString(),
                                binding.inputLocation.text.toString(),
                                LocalDate.parse(date).plusWeeks(x.toLong()).toString(),
                                if (isAllDayChecked) "all-day" else binding.inputTimeFrom.text.toString(),
                                if (isAllDayChecked) "" else binding.inputTimeTo.text.toString(),
                                binding.eventUrl.text.toString(),
                                binding.eventNotes.text.toString()
                            )
                        }
                    }
                    3 -> {
                        Log.i(TAG, "it :$it")
                        for (x: Int in (1..it)) {
                            Log.i(TAG, " x : $x")
                            scheduleEventShareViewModel.addNewItem(
                                binding.inputTitle.text.toString(),
                                binding.inputLocation.text.toString(),
                                LocalDate.parse(date).plusMonths(x.toLong()).toString(),
                                if (isAllDayChecked) "all-day" else binding.inputTimeFrom.text.toString(),
                                if (isAllDayChecked) "" else binding.inputTimeTo.text.toString(),
                                binding.eventUrl.text.toString(),
                                binding.eventNotes.text.toString()
                            )
                        }
                    }
                    else -> Log.i(TAG, "Default")
                }


            }


//
        }

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
            allDay.isChecked = event.time_from == "all-day"

            if (event.time_from == "all-day") {
                allDay.isChecked = true
                preloadTime()
            } else {
                allDay.isChecked = false
                inputTimeFrom.text = event.time_from
                inputTimeTo.text = event.time_to
            }

            pickTime.isVisible = !allDay.isChecked
        }
    }

    fun cancelEvent() {
        Log.i(TAG, "$TAG: cancel Event button was clicked")
        findNavController().navigateUp()
    }

    fun createModifyEvent() {

        val (date, timeFrom, timeTo) = Triple(
            binding.inputDate.text.toString(),
            binding.inputTimeFrom.text.toString(),
            binding.inputTimeTo.text.toString()
        )

        Log.d(TAG, "$TAG: $date, $timeFrom, $timeTo, ${navigationArgs.eventId}")

        lifecycle.coroutineScope.launch {
            scheduleEventShareViewModel.eventConflicts(
                date,
                timeFrom,
                timeTo,
                navigationArgs.eventId
            ).collect {
                when {
                    it.isNotEmpty() -> {
                        Log.i(TAG, "$TAG: Conflicts!")
                        showConflictDialog(it)
                    }
                    navigationArgs.eventId > 0 -> {
                        Log.i(TAG, "$TAG: update Event button was clicked")
                        updateEvent()
                    }
                    else -> {
                        Log.i(TAG, "$TAG: add Event button was clicked")
                        //Snackbar.make(binding.root, R.string.Event_created, Snackbar.LENGTH_SHORT).show()
                        addNewEvent()
                    }
                }
            }
        }


        // testing


    }

    fun showDatePicker() {
        val date = binding.inputDate.text.toString()
        DatePickerFragment(date).show(childFragmentManager, DatePickerFragment.DATE_PICKER)
    }

    fun showTimeFromPicker() {
        val timeFrom = binding.inputTimeFrom.text.toString()
        TimePickerFragment(timeFrom).show(childFragmentManager, TimePickerFragment.TIME_FROM_PICKER)
    }

    fun showTimeToPicker() {
        val timeTo = binding.inputTimeTo.text.toString()
        TimePickerFragment(timeTo).show(childFragmentManager, TimePickerFragment.TIME_TO_PICKER)
    }

    private fun showConflictDialog(conflictEvents: List<ScheduleEvent>) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        builder.setTitle("Conflict Found")

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
        return if (binding.inputTimeFrom.text.toString() != "all-day" && binding.inputTimeFrom.text.toString() != "" && binding.inputTimeTo.text.toString() != "") {
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
        scheduleEventShareViewModel.pickTimeTo(
            LocalTime.now().plusHours(1).format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 5)
        )
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


}