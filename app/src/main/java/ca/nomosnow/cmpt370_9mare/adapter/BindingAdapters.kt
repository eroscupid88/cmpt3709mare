package ca.nomosnow.cmpt370_9mare.adapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.nomosnow.cmpt370_9mare.data.Day
import ca.nomosnow.cmpt370_9mare.ui.calendar.MonthCalendarAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: ArrayList<Day>?) {
    val adapter = recyclerView.adapter as MonthCalendarAdapter
    adapter.submitList(data)
}