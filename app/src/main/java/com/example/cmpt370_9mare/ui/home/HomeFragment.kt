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
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent

import com.example.cmpt370_9mare.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.reduce

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
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            //viewModel = homeViewModel
            homeFragment = this@HomeFragment
        }

        val expandableListView = binding.expandableListView
        viewModel.allEvents.observe(this.viewLifecycleOwner) { items ->
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

        for (event in listSchedule) {
            if (event.date == homeViewModel.getToday()) {
                todayEvents.add(event.time_from+" to "+event.time_to+"      "+event.title)
            }
        }
        listData["Today Event"] = todayEvents
        return listData
    }




    fun goToNextDay() {
        homeViewModel.getNextDay()
    }

    fun gotoPreviousDay() {
        homeViewModel.getPreviousDay()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}