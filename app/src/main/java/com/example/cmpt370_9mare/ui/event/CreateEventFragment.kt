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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "createEventFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateEventFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    /**
     * get Singleton scheduleEventViewModel shared throughout fragments
     */
    private val scheduleEventShareViewModel: ScheduleEventViewModel by activityViewModels {
        ScheduleEventViewModelFactory(
            (activity?.application as ScheduleApplication).database.scheduleEventDao()
        )
    }

    private val calendarViewModel: CalendarViewModel by activityViewModels()

    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!

    /**
     * create CreateEventFragment instance and accept params argument from another fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /**
     * binding FragmentCreateEventBinding and inflate view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        setInputBinding()

    }

    /**
     * setInputBinding function to call handleKeyEvent if Enter button is clicked, it close down keyboard
     * better for user experience
     */
    private fun setInputBinding() {
        binding.inputTitle.setOnKeyListener() { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.inputLocation.setOnKeyListener() { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.eventUrl.setOnKeyListener() { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.eventNotes.setOnKeyListener() { view, keyCode, _ ->
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateEventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateEventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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

    private fun addNewEvent() {
        if (isEntryValid()) {
            scheduleEventShareViewModel.addNewItem(
                binding.inputTitle.text.toString(),
                binding.inputLocation.text.toString(),
                binding.inputDayFrom.text.toString(),
                binding.inputDayTo.text.toString(),
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

    fun cancelEvent() {
        Log.i(TAG, "$TAG: cancel Event button was clicked")
        val action = CreateEventFragmentDirections.actionCreateEventFragmentToNavigationCalendar()
        findNavController().navigate(action)
    }

    fun createEvent() {
        Log.i(TAG, "$TAG: add Event button was clicked")
        //Snackbar.make(binding.root, R.string.Event_created, Snackbar.LENGTH_SHORT).show()
        addNewEvent()
    }

    fun onSelectRepeat() {
        val action = CreateEventFragmentDirections.actionCreateEventFragmentToNewEventFragment()
        findNavController().navigate(action)
    }

    fun showDatePicker(v: View) {
        DatePickerFragment().show(childFragmentManager, "datePicker")
    }

    fun showTimePicker(v: View) {
        TimePickerFragment().show(childFragmentManager, "timePicker")
    }


}