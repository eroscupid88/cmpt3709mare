package com.example.cmpt370_9mare.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: ScheduleEventViewModel by activityViewModels {
        ScheduleEventViewModelFactory(
            (activity?.application as ScheduleApplication).database.scheduleEventDao()
        )
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.eventListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val dashboardAdapter = DashboardAdapter {
            val action =
                DashboardFragmentDirections.actionNavigationDashboardToCreateEventFragment(it.id)
            this.findNavController().navigate(action)
        }

        recyclerView.adapter = dashboardAdapter
        // Attach an observer on the allItems list to update the UI automatically when the data changes.
        viewModel.allEvents.observe(this.viewLifecycleOwner) { items ->
            items.let {
                dashboardAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}