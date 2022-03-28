package ca.nomosnow.cmpt370_9mare.ui.calendar

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.data.Day
import com.example.cmpt370_9mare.databinding.CalendarCellLayoutBinding
import java.time.LocalDate

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
@RequiresApi(Build.VERSION_CODES.O)
class MonthCalendarAdapter(
    private val viewModel: CalendarViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val onItemClicked: (Day) -> Unit
) :
    ListAdapter<Day, MonthCalendarAdapter.DayViewHolder>(DiffCallback) {


    inner class DayViewHolder(
        private var binding: CalendarCellLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Day) {
//            binding.dotDay.setImageResource(android.R.color.transparent)
            if (day.date == LocalDate.now()) {
                binding.cellDayText.setTextColor(
                    ContextCompat.getColor(
                        binding.cellDayText.context,
                        R.color.red_900
                    )
                )
            }

            viewModel.selectDate.observe(lifecycleOwner) {
                if (it == day.date) {
                    binding.dateBackgroundId.visibility = View.VISIBLE
                } else {
                    binding.dateBackgroundId.visibility = View.INVISIBLE
                }
            }

            viewModel.datesWithEventInMonth.observe(lifecycleOwner) {
                if (it.contains(day.date.toString())) {
                    binding.dotDay.visibility = View.VISIBLE
                } else {
                    binding.dotDay.visibility = View.INVISIBLE
                }
            }

            binding.apply {
                this.day = day
                lifecycleOwner = this@MonthCalendarAdapter.lifecycleOwner
                executePendingBindings()
            }

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Day>() {
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return (oldItem.date == newItem.date) && (oldItem.date != null)
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            CalendarCellLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     *
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MonthCalendarAdapter.DayViewHolder, position: Int) {
        val day = getItem(position)

        if (day.day != null) {
            holder.itemView.setOnClickListener {
                onItemClicked(day)
            }
        }

        holder.bind(day)
    }
}
