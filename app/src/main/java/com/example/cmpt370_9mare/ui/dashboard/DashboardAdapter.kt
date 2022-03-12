package com.example.cmpt370_9mare.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.data.schedule_event.getFormattedTime
import com.example.cmpt370_9mare.databinding.EventViewBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */
class DashboardAdapter(private val onItemClicked: (ScheduleEvent) -> Unit) :
    ListAdapter<ScheduleEvent, DashboardAdapter.DashboardViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return DashboardViewHolder(
            EventViewBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class DashboardViewHolder(private var binding: EventViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: ScheduleEvent) {
            binding.eventId.text = event.id.toString()
            binding.showEventName.text = event.title
            binding.showEventTime.text = event.getFormattedTime(event.date_from, event.time_from)
        }
    }

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
}