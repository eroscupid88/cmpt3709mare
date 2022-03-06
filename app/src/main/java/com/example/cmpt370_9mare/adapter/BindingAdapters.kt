package com.example.cmpt370_9mare.adapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.ui.calendar.Day

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: ArrayList<Day>?) {
    val adapter = recyclerView.adapter as MonthCalendarAdapter
    adapter.submitList(data)
}