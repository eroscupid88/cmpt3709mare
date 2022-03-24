package com.example.cmpt370_9mare.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.databinding.CalendarCellLayoutBinding
import com.example.cmpt370_9mare.data.Day

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class MonthCalendarAdapter(private val onItemClicked: (Day) -> Unit) :
    ListAdapter<Day, MonthCalendarAdapter.DayViewHolder>(DiffCallback) {


    class DayViewHolder(
        private var binding: CalendarCellLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Day) {
            binding.day = day
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Day>() {
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayViewHolder {
        return DayViewHolder(
            CalendarCellLayoutBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     *
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(day)
        }
        holder.bind(day)
    }
}
