
package com.example.cmpt370_9mare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.databinding.CalendarCellLayoutBinding
import com.example.cmpt370_9mare.ui.calendar.Day

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class MonthCalendarAdapter :
    ListAdapter<Day, MonthCalendarAdapter.DayViewHolder>(DiffCallback) {

    class DayViewHolder(
        private var binding: CalendarCellLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Day) {
            binding.day = day
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [MarsPhoto] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Day>() {
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem.day == newItem.day
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
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = getItem(position)
        holder.bind(day)
    }
}
