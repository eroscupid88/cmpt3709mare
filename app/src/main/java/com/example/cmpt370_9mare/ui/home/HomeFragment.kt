package com.example.cmpt370_9mare.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory

import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent

import com.example.cmpt370_9mare.databinding.FragmentHomeBinding
import com.example.cmpt370_9mare.ui.calendar.CalendarFragmentDirections
import com.example.cmpt370_9mare.ui.dashboard.DashboardAdapter
import com.example.cmpt370_9mare.ui.dashboard.DashboardFragmentDirections
import com.example.cmpt370_9mare.ui.dashboard.HomeAdapter

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
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
            homeFragment = this@HomeFragment
        }

        recyclerView = binding.homeListRecyclerView

        var homeAdapter = HomeAdapter { }

        recyclerView.adapter = homeAdapter
        // Attach an observer on the allItems list to update the UI automatically when the data changes.
        viewModel.allEvents.observe(this.viewLifecycleOwner) { items ->
            items.let {
                var fake: ScheduleEvent = ScheduleEvent(
                    -1, "fake", "fake",
                    "fake", "fake", "fake", "fake", "fake"
                )
                var list: MutableList<ScheduleEvent> = mutableListOf<ScheduleEvent>()
                var i: Int = 1
                for (event in it) {
                    if (event.date == homeViewModel.getToday()) {
                        list.add(event)
                    }
                }
                homeAdapter.submitList(list)
            }
        }

    }


    private fun updateEvent() {
        recyclerView = binding.homeListRecyclerView
        var list: MutableList<ScheduleEvent> = mutableListOf<ScheduleEvent>()
        val homeAdapter = HomeAdapter { }
        for (event in homeAdapter.currentList) {
            if (event.date == homeViewModel.getToday()) {
                //list.add(event)
            }
        }
        homeAdapter.submitList(list)
    }

    fun goToNextDay() {
        homeViewModel.getNextDay()
        updateEvent()
    }

    fun gotoPreviousDay() {
        homeViewModel.getPreviousDay()
        updateEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}