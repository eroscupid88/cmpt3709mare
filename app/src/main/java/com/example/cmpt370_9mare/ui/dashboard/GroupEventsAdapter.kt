package com.example.cmpt370_9mare.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.DailyEventViewBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */
class GroupEventsAdapter(
    private val onItemClicked: (ScheduleEvent) -> Unit
) : ListAdapter<ScheduleEvent, GroupEventsAdapter.GroupEventsViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ScheduleEvent>() {
            override fun areItemsTheSame(oldItem: ScheduleEvent, newItem: ScheduleEvent): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: ScheduleEvent,
                newItem: ScheduleEvent
            ): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupEventsViewHolder {
        return GroupEventsViewHolder(
            DailyEventViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GroupEventsViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    /**
     * ViewHolder to hold data of DashboardFragment
     */
    class GroupEventsViewHolder(private var binding: DailyEventViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind views on DashboardFragment to the events' data
         */
        fun bind(event: ScheduleEvent) {
            binding.apply {
                dailyEventTitle.text = event.title
                dailyEventTimeStart.text = event.time_from
                dailyEventTimeEnd.text = event.time_to
            }
        }
    }
}