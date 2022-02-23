package com.example.cmpt370_9mare

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmpt370_9mare.adapter.CalendarAdapter
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), OnItemListener {
    private var calendarRecycleView: RecyclerView? = null
    private var monthYearText: TextView? = null
    private var selectedDate: LocalDate? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        selectedDate = LocalDate.now()
        logging()
        setMonthView()


    }

    private fun logging(){
        Log.i(TAG, "INFO: calendar app up and running")
        Log.i(TAG, "INFO: current local date is : $selectedDate")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate?): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date?.format(formatter)
    }

    private fun initWidgets() {
        calendarRecycleView = findViewById(R.id.calendarRecycleView)
        monthYearText = findViewById(R.id.month_year)
    }

    @RequiresApi(Build.VERSION_CODES.O)

    private fun setMonthView() {
        monthYearText!!.setText(monthYearFromDate(selectedDate).toString())
        val daysInMonth: ArrayList<String> = daysInMonthArray(selectedDate!!)
        for (x:String in daysInMonth){
            Log.d(TAG,"$x")
        }
        calendarRecycleView!!.adapter = CalendarAdapter(daysInMonth)
        calendarRecycleView!!.layoutManager= GridLayoutManager(applicationContext,7)

        Log.d(TAG,"DEBUG: calling MainActivity/setMonthView()")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthArray(date:LocalDate): ArrayList<String> {
        // maximum 31 days in month
        var daysInMonthArray: ArrayList<String> = ArrayList(35)
        var yearMonth: YearMonth = YearMonth.from(date)
        var daysInMonth: Int = yearMonth.lengthOfMonth()
        var firstOfMonth : LocalDate = selectedDate!!.withDayOfMonth(1)
        var dayOfWeek : Int = firstOfMonth.dayOfWeek.value
        for (x :Int in 1..42) {
            Log.d(TAG, "DEBUG: checking week $x")
            when{
                 x <= dayOfWeek || x > daysInMonth+dayOfWeek-> daysInMonthArray.add("")
                else -> daysInMonthArray.add((x - dayOfWeek).toString())
            }
        }
        Log.d(TAG,"DEBUG: $yearMonth, $daysInMonth,$firstOfMonth,$dayOfWeek,$selectedDate")

        return daysInMonthArray
    }

    fun nextMonthAction(view: View) {}
    fun previousMonthAction(view: View) {}
    override fun onItemClick(position: Int, dayText: String) {
        TODO("Not yet implemented")
    }
}


