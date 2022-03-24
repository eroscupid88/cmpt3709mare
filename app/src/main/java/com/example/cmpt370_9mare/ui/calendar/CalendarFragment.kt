package com.example.cmpt370_9mare.ui.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import com.example.cmpt370_9mare.data.Day
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.FragmentCalendarBinding
import com.example.cmpt370_9mare.ui.dashboard.DashboardFragmentDirections
import com.example.cmpt370_9mare.ui.dashboard.ShowEventDetailsFragment

private const val TAG = "CalendarFragment"

@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragment : Fragment() {
    // Binding
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CalendarViewModel by activityViewModels()
    private val sharedScheduleEvent: ScheduleEventViewModel by activityViewModels {
        ScheduleEventViewModelFactory((activity?.application as ScheduleApplication).database.scheduleEventDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        // Giving the binding access to the OverviewViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            calendarFragment = this@CalendarFragment
        }

        // Initialize the monthCalendarGrid adapter
        binding.monthCalendarGrid.adapter = MonthCalendarAdapter {
//            sharedViewModel.setCurrentedDate(it.date.toString())
            initializeDailyEventAdapter(sharedScheduleEvent.eventFromDate(it.date.toString()))
            Log.d(TAG, "clicked: ${it.date.toString()}")
        }

        binding.dailyEventList.layoutManager = LinearLayoutManager(this.context)

        initializeDailyEventAdapter(sharedScheduleEvent.eventFromDate(sharedScheduleEvent.today))

        binding.floatingActionButton.setOnClickListener {
            val action = CalendarFragmentDirections.actionNavigationCalendarToCreateEventFragment(
                eventId = -1
            )
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeDailyEventAdapter(events: LiveData<List<ScheduleEvent>>) {
        val dailyEventAdapter = DailyEventCalendarAdapter {
            Log.d(TAG, "clicked: ${it.title}")

            val action =
                CalendarFragmentDirections.actionNavigationCalendarToCreateEventFragment(it.id)

            ShowEventDetailsFragment(it, action).show(
                childFragmentManager,
                ShowEventDetailsFragment.EVENT_DETAILS
            )
        }

        binding.dailyEventList.adapter = dailyEventAdapter
        events.observe(this.viewLifecycleOwner) { items ->
            items.let {
                dailyEventAdapter.submitList(it)
            }
        }
    }

    /**
     * goToNextMonth function call next month action which is load next month calendar
     */
    fun goToNextMonth() {
        sharedViewModel.nextMonthAction()
    }

    /**
     * goToPreviousMonth function call previous month action which is load previous month calendar
     */
    fun goToPreviousMonth() {
        sharedViewModel.previousMonthAction()
    }

    fun setCurrentDateBackground() {

    }

    fun setCurrentedDate(day: Day) {

    }

}


//
//
////package com.example.cmpt370_9mare.ui.calendar
////
////import android.os.Build
////import android.os.Bundle
////import android.util.Log
////import android.view.*
////import androidx.fragment.app.Fragment
////import android.widget.TextView
////import androidx.annotation.RequiresApi
////import androidx.core.content.ContextCompat
////import androidx.recyclerview.widget.GridLayoutManager
////import androidx.recyclerview.widget.RecyclerView
////import com.example.cmpt370_9mare.R
////import com.example.cmpt370_9mare.adapter.CalendarAdapter
////import com.example.cmpt370_9mare.databinding.FragmentCalendarBinding
////import java.time.LocalDate
////import java.time.YearMonth
////import java.time.format.DateTimeFormatter
////
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
//
//
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
////}