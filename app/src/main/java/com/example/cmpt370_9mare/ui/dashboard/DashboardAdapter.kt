package com.example.cmpt370_9mare.ui.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.FragmentEventDetailsBinding
import com.example.cmpt370_9mare.databinding.GroupEventsViewBinding
import com.example.cmpt370_9mare.ui.calendar.DailyEventCalendarAdapter

/**
 * [ListAdapter] implementation for the recyclerview.
 */
class DashboardAdapter(private val fragmentManager: FragmentManager) :
    ListAdapter<DashboardGroupEvents, DashboardAdapter.DashboardViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DashboardGroupEvents>() {
            override fun areItemsTheSame(
                oldItem: DashboardGroupEvents,
                newItem: DashboardGroupEvents
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DashboardGroupEvents,
                newItem: DashboardGroupEvents
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return DashboardViewHolder(
            GroupEventsViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    /**
     * ViewHolder to hold data of DashboardFragment
     */
    inner class DashboardViewHolder(private var binding: GroupEventsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind views on DashboardFragment to the events' data
         */
        fun bind(group: DashboardGroupEvents) {
            val dailyEventAdapter = DailyEventCalendarAdapter {
                ShowEventDetailsFragment(it).show(fragmentManager, "Event Details")
            }

            binding.dateOfGroup.text = group.date
            binding.groupList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = dailyEventAdapter
            }

            group.events.observe(itemView.context as LifecycleOwner) {
                dailyEventAdapter.submitList(it)
            }
        }
    }
}

class ShowEventDetailsFragment(private val event: ScheduleEvent) : DialogFragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            _binding = FragmentEventDetailsBinding.inflate(layoutInflater)

            // Inflate and set the layout for the dialog
            builder.setView(binding.root)
                // Add action buttons
                .setPositiveButton(R.string.edit) { dialog, _ ->
                    dialog.cancel()
                    val action =
                        DashboardFragmentDirections.actionNavigationDashboardToCreateEventFragment(
                            event.id
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
                .setNeutralButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            titleValue.text = event.title
            locationValue.text = event.location
            dateValue.text = event.date
            timeValue.text =
                if (event.time_from == "all-day") "All-Day" else "${event.time_from} to ${event.time_to}"
            urlValue.text = event.url
            notesValue.text = event.notes
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}