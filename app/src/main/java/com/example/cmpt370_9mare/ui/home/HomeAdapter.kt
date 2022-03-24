package com.example.cmpt370_9mare.ui.home

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
class HomeAdapter(private val onItemClicked: (ScheduleEvent) -> Unit) :
    ListAdapter<ScheduleEvent, HomeAdapter.HomeViewHolder>(DiffCallback) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            EventViewBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    /**
     * ViewHolder to hold data of DashboardFragment
     */
    class HomeViewHolder(private var binding: EventViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind views on DashboardFragment to the events' data
         */
        fun bind(event: ScheduleEvent) {
            binding.showEventName.text = event.title
            binding.showEventTime.text = getFormattedTime(event.date, event.time_from)
        }
    }
}