package com.example.cmpt370_9mare.ui.home

import androidx.fragment.app.activityViewModels
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent

import com.example.cmpt370_9mare.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.reduce
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val viewModel: ScheduleEventViewModel by activityViewModels {
        ScheduleEventViewModelFactory(
            (activity?.application as ScheduleApplication).database.scheduleEventDao()
        )
    }

    private lateinit var recyclerView: RecyclerView
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val today = LocalDate.now().dayOfMonth
        val month = LocalDate.now().month
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
            homeFragment = this@HomeFragment
            dayNumber.text = today.toString()
            thisMonth.text = month.toString()
        }

        val expandableListView = binding.expandableListView
        viewModel.TodayAndFutureEvents.observe(this.viewLifecycleOwner) { items ->
            items.let {
                val listData = getData(it)
                titleList = ArrayList(listData.keys)
                adapter = HomeExpandableAdapter(
                    this.requireContext().applicationContext,
                    titleList as ArrayList<String>,
                    listData
                )
                expandableListView.setAdapter(adapter)
            }
        }
    }

    private fun getData(listSchedule: List<ScheduleEvent>): HashMap<String, List<String>> {
        val listData = HashMap<String, List<String>>()
        val todayEvents = ArrayList<String>()
        val nextEvent = ArrayList<String>()
        var lastEvent = 0
        // get today event
        for (event in listSchedule) {
            if (event.date == homeViewModel.getToday()) {
                todayEvents.add(event.time_from + " to " + event.time_to + "      " + event.title)
                lastEvent = event.id
            }
            //if (event.id > homeViewModel.getToday(). )
        }
        if (todayEvents.isEmpty()) {
            todayEvents.add("No events for Today")
            if (listSchedule.isNotEmpty()) {
            nextEvent.add(listSchedule[0].date + "            " + listSchedule[0].title)}
            else{
                nextEvent.add("No event for future")
            }
        } else {
            for (event in listSchedule) {
                if (event.id == lastEvent + 1) {
                    nextEvent.add(event.date + "            " + event.title)
                }
            }
        }
        if (nextEvent.isEmpty()) {
            nextEvent.add("No event for the Future")
        }

        // get future event
        listData["Today Event"] = todayEvents
        listData["Next Event"] = nextEvent


        return listData
    }


    fun monthDisplay() {
        homeViewModel.monthDisplay()
    }

    fun dayDisplay() {
        homeViewModel.dayDisplay()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}