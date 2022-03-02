package com.example.cmpt370_9mare.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.R


class CalendarAdapter(
    private var daysOfMonth: ArrayList<String>
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val dayOfMonth: TextView? = view?.findViewById(R.id.cellDayText)
        val previousMonthButton = view?.findViewById<Button>(R.id.previous_month_button)
        val nextMonthButton = view?.findViewById<Button>(R.id.next_month_button)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view : View?= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.calendar_cell,parent,false)
        val layoutPrams: ViewGroup.LayoutParams? = view!!.layoutParams
        layoutPrams!!.height = (parent.height * 0.1).toInt()
        view.accessibilityDelegate = Accessibility
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth?.text = daysOfMonth[position]
        holder.dayOfMonth?.setOnClickListener {
//            val action = LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(letter= holder.button.text.toString())
//            holder.view.findNavController().navigate(action)
        }
        holder.previousMonthButton?.setOnClickListener {

        }
        holder.nextMonthButton?.setOnClickListener {  }
    }


    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    // Setup custom accessibility delegate to set the text read with
    // an accessibility service
    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(
            host: View?,
            info: AccessibilityNodeInfo?
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
            val customString = host?.context?.getString(R.string.hello)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info?.addAction(customClick)
        }
    }



}