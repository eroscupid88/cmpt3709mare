package com.example.cmpt370_9mare.ui.event

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cmpt370_9mare.*
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.FragmentCreateEventBinding
import com.example.cmpt370_9mare.ui.calendar.CalendarViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val TAG = "createEventFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!

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
        scheduleEventShareViewModel.pickTimeFrom("")
        scheduleEventShareViewModel.pickTimeTo("")
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = scheduleEventShareViewModel
            createEventFragment = this@CreateEventFragment
        }

        val id = navigationArgs.eventId
        if (id > 0) {
            binding.apply {
                calendarTitle.text = getString(R.string.modify_event_title)
                submitCreateEvent.text = getString(R.string.update_button_text)
            }
            scheduleEventShareViewModel.eventFromId(id)
                .observe(this.viewLifecycleOwner) { selectedItem ->
                    currentEvent = selectedItem
                    bind(currentEvent)
                }
        }

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
        if (isEntryValid()) {
            currentEvent.apply {
                title = binding.inputTitle.text.toString()
                location = binding.inputLocation.text.toString()
                date = binding.inputDate.text.toString()
                time_from = binding.inputTimeFrom.text.toString()
                time_to = binding.inputTimeTo.text.toString()
                url = binding.eventUrl.text.toString()
                notes = binding.eventNotes.text.toString()
            }
            scheduleEventShareViewModel.updateItem(currentEvent)
            findNavController().navigateUp()
        }

    }

    private fun addNewEvent() {
        if (isEntryValid()) {
            //TODO: Create Events for all repeat cases up to 1 year.
            /*val test = arguments?.getString(ARG_REPEAT)
            Log.i(TAG, "$TAG: $test")
            when (test) {

            }*/

            scheduleEventShareViewModel.addNewItem(
                binding.inputTitle.text.toString(),
                binding.inputLocation.text.toString(),
                binding.inputDate.text.toString(),
                binding.inputTimeFrom.text.toString(),
                binding.inputTimeTo.text.toString(),
                binding.eventUrl.text.toString(),
                binding.eventNotes.text.toString()
            )
            val action =
                CreateEventFragmentDirections.actionCreateEventFragmentToNavigationCalendar()
            findNavController().navigate(action)
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
            inputTimeFrom.text = event.time_from
            inputTimeTo.text = event.time_to
            eventUrl.setText(event.url, TextView.BufferType.SPANNABLE)
            eventNotes.setText(event.notes, TextView.BufferType.SPANNABLE)
        }
    }

    private fun checkConflicts(date: String, timeFrom: String, timeTo: String): Boolean {
        //TODO: Check for time conflicts
        return false
    }

    fun cancelEvent() {
        Log.i(TAG, "$TAG: cancel Event button was clicked")
        findNavController().navigateUp()
    }

    fun createmodifyEvent() {
        if (checkConflicts( binding.inputDate.text.toString(),
            binding.inputTimeFrom.text.toString(),
            binding.inputTimeTo.text.toString())) {
            if (navigationArgs.eventId > 0) {
                Log.i(TAG, "$TAG: update Event button was clicked")
                updateEvent()
            } else {
                Log.i(TAG, "$TAG: add Event button was clicked")
                //Snackbar.make(binding.root, R.string.Event_created, Snackbar.LENGTH_SHORT).show()
                addNewEvent()
            }
        }
        else {
            //TODO: Make Alert for conflicting times
        }
    }

    fun onSelectRepeat() {
        val action = CreateEventFragmentDirections.actionCreateEventFragmentToNewEventFragment()
        findNavController().navigate(action)
    }

    fun showDatePicker() {
        DatePickerFragment().show(childFragmentManager, DatePickerFragment.DATE_PICKER)
    }

    fun showTimeFromPicker() {
        TimePickerFragment().show(childFragmentManager, TimePickerFragment.TIME_FROM_PICKER)
    }

    fun showTimeToPicker() {
        TimePickerFragment().show(childFragmentManager, TimePickerFragment.TIME_TO_PICKER)
    }
}