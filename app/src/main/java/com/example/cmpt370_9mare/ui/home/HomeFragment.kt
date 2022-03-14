package com.example.cmpt370_9mare.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.databinding.FragmentHomeBinding
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

        val homeAdapter = HomeAdapter{ }

        recyclerView.adapter = homeAdapter
        // Attach an observer on the allItems list to update the UI automatically when the data changes.
        viewModel.allEvents.observe(this.viewLifecycleOwner) { items ->
            items.let {
                val list = it
                for(event in it ) {
                    Log.i("it", event.date)
                    Log.i("it", event.title)
                    Log.i("home", homeViewModel.getToday())
                }
                homeAdapter.submitList(list)
            }
        }

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