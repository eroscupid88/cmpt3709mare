package com.example.cmpt370_9mare.ui.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.FragmentDashboardBinding

private const val TAG = "dashboard"
private const val FUTURE = "future"
private const val PAST = "past"
private const val SEARCH = "search"

class DashboardFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: ScheduleEventViewModel by activityViewModels {
        ScheduleEventViewModelFactory(
            (activity?.application as ScheduleApplication).database.scheduleEventDao()
        )
    }

    private lateinit var searchedName: String

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.eventListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        initializeDashboardAdapter(viewModel.futureEvents)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Determines how to handle interactions with the selected [MenuItem]
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.search_event -> {
                Log.d(TAG, "searchEvent button clicked")
                showSearchDialog()
                return true
            }
            R.id.show_future_events -> {
                Log.d(TAG, "showFutureEvents button clicked")
                showEvents(FUTURE)
            }
            R.id.show_past_events -> {
                Log.d(TAG, "showPastEvents button clicked")
                showEvents(PAST)
            }
            //  Otherwise, do nothing and use the core event handling
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeDashboardAdapter(events: LiveData<List<ScheduleEvent>>) {
        val dashboardAdapter = DashboardAdapter {
            val action =
                DashboardFragmentDirections.actionNavigationDashboardToCreateEventFragment(it.id)
            this.findNavController().navigate(action)
        }

        recyclerView.adapter = dashboardAdapter
        // Attach an observer on the event list to update the UI automatically when the data changes.
        events.observe(this.viewLifecycleOwner) { items ->
            items.let {
                dashboardAdapter.submitList(it)
            }
        }
    }

    private fun showEvents(type: String): Boolean {
        when (type) {
            FUTURE -> initializeDashboardAdapter(viewModel.futureEvents)
            PAST -> initializeDashboardAdapter(viewModel.pastEvent)
            else -> initializeDashboardAdapter(viewModel.searchedEvents)
        }

        return true
    }

    private fun showSearchDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        builder.setTitle("Search for Events by Name")
        // Set up the input
        val input = EditText(this.context)
        // Specify the type of input expected
        input.hint = "Enter Event Name"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("Search") { _, _ ->
            val eventName = String.format("%%${input.text}%%")
            if (eventName != "%%") {
                viewModel.searchEvent(eventName)
                showEvents(SEARCH)
            } else {
                initializeDashboardAdapter(MutableLiveData<List<ScheduleEvent>>())
            }

        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}