package com.example.cmpt370_9mare.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.data.Day
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.DailyEventViewBinding

class DailyEventCalendarAdapter(private val onItemClicked:(ScheduleEvent)->Unit):
ListAdapter<ScheduleEvent,DailyEventCalendarAdapter.DailyEventViewHolder>(DiffCallback){


    inner class DailyEventViewHolder(private var binding:DailyEventViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(scheduleEvent: ScheduleEvent) {
            binding.apply {
                dailyEventTitle.text =  scheduleEvent.title
                dailyEventTimeStart.text =  scheduleEvent.time_from
                dailyEventTimeEnd.text = scheduleEvent.time_to
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyEventCalendarAdapter.DailyEventViewHolder {
        return DailyEventViewHolder(DailyEventViewBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(
        holder: DailyEventCalendarAdapter.DailyEventViewHolder,
        position: Int
    ) {
        val scheduleEvent = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(scheduleEvent)
        }
        holder.bind(scheduleEvent)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<ScheduleEvent>() {
        override fun areItemsTheSame(oldItem: ScheduleEvent, newItem: ScheduleEvent): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: ScheduleEvent, newItem: ScheduleEvent): Boolean {
            return oldItem == newItem
        }
    }
}