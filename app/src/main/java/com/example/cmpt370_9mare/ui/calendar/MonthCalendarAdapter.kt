
package com.example.cmpt370_9mare.ui.calendar

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.databinding.CalendarCellLayoutBinding
import com.example.cmpt370_9mare.data.Day
import java.time.LocalDate
import java.util.*

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
@RequiresApi(Build.VERSION_CODES.O)
class MonthCalendarAdapter(private val onItemClicked:(Day)->Unit) :
    ListAdapter<Day, MonthCalendarAdapter.DayViewHolder>(DiffCallback) {

    class DayViewHolder(
        var binding: CalendarCellLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: Day) {
            binding.day = day
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
//            holder.binding.dateBackgroundId.setImageResource(
//                R.drawable.date_background_current
//            )
        }
        holder.bind(day)
    }
}
