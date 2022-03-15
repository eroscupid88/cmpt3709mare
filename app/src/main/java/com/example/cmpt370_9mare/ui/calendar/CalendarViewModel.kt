package com.example.cmpt370_9mare.ui.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cmpt370_9mare.data.Day
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


private const val TAG = "CalendarViewModel"

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel : ViewModel() {
    private val _monthYearText = MutableLiveData<String>()
    private var _currentedDate : String = ""
    private val _daysOfTheMonth = MutableLiveData<ArrayList<Day>?>()
    private var _currentDate = LocalDate.now()
    private var selectDate: LocalDate? = null

    val monthYearText: LiveData<String> = _monthYearText
    val daysOfTheMonth: MutableLiveData<ArrayList<Day>?> = _daysOfTheMonth
    val currentedDate: String get() = _currentedDate


    fun setCurrentedDate(date: String) {
        _currentedDate = date
    }

    init {
        logging()
        selectDate = LocalDate.now()
        _currentedDate = LocalDate.now().toString()
        setMonthYearText()
        _daysOfTheMonth.value = daysInMonthArray(selectDate!!)
    }


    private fun setMonthYearText() {
        _monthYearText.value = monthYearFromDate(selectDate!!).toString()
    }

    private fun logging() {
        Log.i(TAG, "INFO/CalendarViewModel: CalendarViewModel up and running")
        Log.i(TAG, "INFO/CalendarViewModel: current local date is : ")
    }

    private fun monthYearFromDate(date: LocalDate?): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date?.format(formatter)
    }


    private fun getCurrentDate(date: LocalDate?): Int? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        val some = date?.format(formatter)
        return some?.takeLast(2)?.toInt()
    }

    fun setSelectedDate(date: LocalDate) {
        selectDate = date
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<Day> {

        // maximum 31 days in month
        val daysInMonthArray = arrayListOf<Day>()

        // current year month from chosen date
        val yearMonth: YearMonth = YearMonth.from(date)

        //number of days in a month
        val daysInMonth: Int = yearMonth.lengthOfMonth()

        // first date of the month

        val firstOfMonth: LocalDate = date.withDayOfMonth(1)
        // number days of week

        val dayOfWeek: Int = firstOfMonth.dayOfWeek.value
        var y = 0
        for (x: Int in 1..42) {
            when {
                x <= dayOfWeek || x > daysInMonth + dayOfWeek -> daysInMonthArray.add(
                    Day(null,null)
                )
                else -> {
                    daysInMonthArray.add(Day((x - dayOfWeek).toString(),firstOfMonth.plusDays(y.toLong())))
                    y ++
                }
            }

        }
        Log.d(TAG, "DEBUG: $yearMonth, $daysInMonth,$firstOfMonth,$dayOfWeek,$selectDate")
        Log.d(TAG, "DEBUG: $daysInMonthArray")

        return daysInMonthArray
    }

    fun nextMonthAction() {
        selectDate = selectDate!!.plusMonths(1)

        _daysOfTheMonth.value = daysInMonthArray(selectDate!!)
        setMonthYearText()
        Log.d(TAG, "DEBUG: NextMonthAction selectedDate: $selectDate")
    }

    fun previousMonthAction() {
        selectDate = selectDate!!.minusMonths(1)
        _daysOfTheMonth.value = daysInMonthArray(selectDate!!)
        setMonthYearText()
        Log.d(TAG, "DEBUG: previousMonthAction selectedDate: $selectDate")
    }



}


//package com.example.cmpt370_9mare.ui.calendar
//
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import android.view.*
//import androidx.fragment.app.Fragment
//import android.widget.TextView
//import androidx.annotation.RequiresApi
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.cmpt370_9mare.R
//import com.example.cmpt370_9mare.adapter.CalendarAdapter
//import com.example.cmpt370_9mare.databinding.FragmentCalendarBinding
//import java.time.LocalDate
//import java.time.YearMonth
//import java.time.format.DateTimeFormatter
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [CalendarFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//
//private const val TAG = "CalendarFragment"
//@RequiresApi(Build.VERSION_CODES.O)
//class CalendarFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var _binding: FragmentCalendarBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var calendarRecycleView: RecyclerView
//
//    // for calendar
//    private var monthYearText: TextView? = null
//    private var selectedDate: LocalDate? = LocalDate.now()
//
//
//    // Fragment cycle set up
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//        // passing arguments
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//        logging()
//
//
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout for this fragment
//        _binding = FragmentCalendarBinding.inflate(inflater,container,false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        initWidgets()
////        setMonthView()
//    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//
//// set Icon
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.bottom_nav_menu,menu)
////        val layoutButton = menu.findItem(R.id.next_month_button)
////        setIcon(layoutButton)
//    }
//    // setIcon menu
//    private fun setIcon(menuItem: MenuItem?) {
//        if (menuItem == null) {
//            return
//        }
//        menuItem.icon = ContextCompat.getDrawable(this.requireContext(),R.drawable.person_menu)
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        Log.i(TAG, "User drop down menu is pressed")
//         return super.onOptionsItemSelected(item)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment CalendarFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            CalendarFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//    private fun logging(){
//        Log.i(TAG, "INFO/CalendarFragment: CalendarFragment up and running")
//        Log.i(TAG, "INFO/CalendarFragment: current local date is : $selectedDate")
//    }
//
//    private fun monthYearFromDate(date: LocalDate?): String? {
//        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
//        return date?.format(formatter)
//    }
////
////    private fun initWidgets() {
////        calendarRecycleView = binding.calendarRecycleView
//////        monthYearText = binding.showMonthYear
////    }
////
////    private fun setMonthView() {
//////        monthYearText!!.setText(monthYearFromDate(selectedDate).toString())
////        val daysInMonth: ArrayList<String> = daysInMonthArray(selectedDate!!)
////        calendarRecycleView.adapter = CalendarAdapter(daysInMonth)
////        calendarRecycleView.layoutManager= GridLayoutManager(context,7)
////        Log.d(TAG,"DEBUG/CalendarFragment: calling CalendarFragment/setMonthView")
////    }
////
////    private fun daysInMonthArray(date:LocalDate): ArrayList<String> {
////        // maximum 31 days in month
////        var daysInMonthArray: ArrayList<String> = ArrayList(35)
////        var yearMonth: YearMonth = YearMonth.from(date)
////        var daysInMonth: Int = yearMonth.lengthOfMonth()
////        var firstOfMonth : LocalDate = selectedDate!!.withDayOfMonth(1)
////        var dayOfWeek : Int = firstOfMonth.dayOfWeek.value
////        for (x :Int in 1..42) {
////            Log.d(TAG, "DEBUG: checking week $x")
////            when{
////                x <= dayOfWeek || x > daysInMonth+dayOfWeek-> daysInMonthArray.add("")
////                else -> daysInMonthArray.add((x - dayOfWeek).toString())
////            }
////        }
////        Log.d(TAG,"DEBUG: $yearMonth, $daysInMonth,$firstOfMonth,$dayOfWeek,$selectedDate")
////
////        return daysInMonthArray
////    }
////
////    fun nextMonthAction(view: View) {
////        selectedDate = selectedDate!!.plusMonths(1)
////        setMonthView()
////    }
////    fun previousMonthAction(view: View) {
////        selectedDate = selectedDate!!.minusMonths(1)
////        setMonthView()
////    }
////    fun eventShowAction(view: View){
////        Log.i(TAG,"eventShowClick!!")
////        TODO("Need to implement click to show event ")
////    }
////    // add something to check
//
//}