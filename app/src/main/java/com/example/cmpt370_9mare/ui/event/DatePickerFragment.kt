package com.example.cmpt370_9mare.ui.event

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.cmpt370_9mare.ScheduleApplication
import com.example.cmpt370_9mare.ScheduleEventViewModel
import com.example.cmpt370_9mare.ScheduleEventViewModelFactory
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        const val DATE_PICKER = "datePicker_tag"
    }

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: ScheduleEventViewModel by activityViewModels {
        ScheduleEventViewModelFactory(
            (activity?.application as ScheduleApplication).database.scheduleEventDao()
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    // Set the pickedDate in the shared ScheduleEventViewModel
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Format month and day to add a 0
        val dateString = String.format("$year-%02d-%02d", month + 1, day)

        viewModel.pickDate(dateString)
    }
}
