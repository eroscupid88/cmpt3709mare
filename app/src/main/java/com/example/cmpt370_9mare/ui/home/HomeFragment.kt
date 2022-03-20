package com.example.cmpt370_9mare.ui.home

import androidx.fragment.app.activityViewModels
import android.os.Build
import android.os.Bundle
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

    val data: HashMap<String, List<String>> get() {
        val listData = HashMap<String, List<String>>()
        val appleMobiles = ArrayList<String>()
        appleMobiles.add(viewModel.today)
        appleMobiles.add("iPhone 8 Plus")
        appleMobiles.add("iPhone X")
        appleMobiles.add("iPhone 7 Plus")
        appleMobiles.add("iPhone 7")
        appleMobiles.add("iPhone 6 Plus")
        listData["Apple"] = appleMobiles
        listData["Apple2"] = appleMobiles
        listData["Apple3"] = appleMobiles
        return listData
    }


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
//
//        recyclerView = binding.homeListRecyclerView
//
//        val homeAdapter = HomeAdapter { }
//
//        recyclerView.adapter = homeAdapter
//        // Attach an observer on the allItems list to update the UI automatically when the data changes.
//        viewModel.allEvents.observe(this.viewLifecycleOwner) { items ->
//            items.let {
//                val list: MutableList<ScheduleEvent> = mutableListOf<ScheduleEvent>()
//                for (event in it) {
//                    if (event.date == homeViewModel.getToday()) {
//                        list.add(event)
//                    }
//                }
//                homeAdapter.submitList(list)
//            }
//        }
        val expandableListView = binding.expandableListView
        val listData = data
        titleList = ArrayList(listData.keys)
        adapter = HomeExpandableAdapter(this.requireContext().applicationContext, titleList as ArrayList<String>, listData)
        expandableListView.setAdapter(adapter)
        //setupExpandableListView();


    }

//    private fun setupExpandableListView() {
//        val expandableListView = binding.expandableListView
//        val listData = data
//        titleList = ArrayList(listData.keys)
//        adapter = HomeExpandableAdapter(requireContext(), titleList as ArrayList<String>, listData)
//        expandableListView.setAdapter(adapter)
//    }


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