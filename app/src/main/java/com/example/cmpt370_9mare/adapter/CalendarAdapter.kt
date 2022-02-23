package com.example.cmpt370_9mare.adapter

import android.app.ActionBar
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.R


class CalendarAdapter(
    private var daysOfMonth: ArrayList<String>
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val dayOfMonth: TextView = view!!.findViewById<TextView?>(R.id.cellDayText)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view : View?= LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell,parent,false)
        val layoutPrams: ViewGroup.LayoutParams? = view!!.layoutParams
        layoutPrams!!.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]
    }

    override fun getItemCount(): Int {

        return daysOfMonth.size
    }



}