package com.example.cmpt370_9mare.ui.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.data.Day
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.FragmentCalendarBinding
import java.time.LocalDateTime
import java.time.LocalTime

private const val TAG = "CalendarFragment"

@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragment : Fragment() {
    // Binding
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CalendarViewModel by activityViewModels()
    private val sharedScheduleEvent: ScheduleEventViewModel by activityViewModels {
        ScheduleEventViewModelFactory((activity?.application as ScheduleApplication).database.scheduleEventDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        // Giving the binding access to the OverviewViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            calendarFragment = this@CalendarFragment
        }

        // Initialize the monthCalendarGrid adapter
        binding.monthCalendarGrid.adapter = MonthCalendarAdapter {
//            sharedViewModel.setCurrentedDate(it.date.toString())
            initializeDailyEventAdapter(sharedScheduleEvent.eventFromDate(it.date.toString()))
            Log.d(TAG, "clicked: ${it.date.toString()}")
        }

        binding.dailyEventList.layoutManager = LinearLayoutManager(this.context)

        initializeDailyEventAdapter(sharedScheduleEvent.eventFromDateAndTime(LocalTime.now().toString(),sharedScheduleEvent.today))

        binding.floatingActionButton.setOnClickListener {
            val action = CalendarFragmentDirections.actionNavigationCalendarToCreateEventFragment(
                eventId = -1
            )
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeDailyEventAdapter(events: LiveData<List<ScheduleEvent>>) {
        val dailyEventAdapter = DailyEventCalendarAdapter {
            Log.d(TAG, "clicked: ${it.title}")

            val action =
                CalendarFragmentDirections.actionNavigationCalendarToCreateEventFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.dailyEventList.adapter = dailyEventAdapter
        events.observe(this.viewLifecycleOwner) { items ->
            items.let {
                dailyEventAdapter.submitList(it)
            }
        }
    }

    /**
     * goToNextMonth function call next month action which is load next month calendar
     */
    fun goToNextMonth() {
        sharedViewModel.nextMonthAction()
    }

    /**
     * goToPreviousMonth function call previous month action which is load previous month calendar
     */
    fun goToPreviousMonth() {
        sharedViewModel.previousMonthAction()
    }
}
