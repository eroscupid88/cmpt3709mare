package ca.nomosnow.cmpt370_9mare.ui.event

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import ca.nomosnow.cmpt370_9mare.R
import ca.nomosnow.cmpt370_9mare.databinding.FragmentCreateEventBinding
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CreateEventInputHandler(
    private val fragment: CreateEventFragment,
    private val binding: FragmentCreateEventBinding,
) {
    val title = binding.inputTitle.text.toString()
    val location = binding.inputLocation.text.toString()
    val group = binding.groupMenuAutocomplete.text.toString()
    val date = binding.inputDate.text.toString()
    val timeFrom = binding.inputTimeFrom.text.toString()
    val timeTo = binding.inputTimeTo.text.toString()
    val url = binding.eventUrl.text.toString()
    val notes = binding.eventNotes.text.toString()

    private fun addEventWithAnotherDate(anotherDate: String) {
        fragment.scheduleEventShareViewModel.addNewItem(
            title,
            location,
            group,
            anotherDate,
            if (binding.allDay.isChecked) fragment.getString(R.string.all_day) else timeFrom,
            if (binding.allDay.isChecked) "" else timeTo,
            url,
            notes
        )
    }

    fun addNewEvent() {
        addEventWithAnotherDate(date)

        // Repeat if selected
        if (binding.repeatButton.isChecked) {
            for (x in (1..binding.spRepeatEveryAutocomplete.text.toString().toLong())) {
                when (binding.spRepetitionIntervalAutocomplete.text.toString()) {
                    "Day(s)" -> addEventWithAnotherDate(
                        LocalDate.parse(date).plusDays(x).toString()
                    )
                    "Week(s)" -> addEventWithAnotherDate(
                        LocalDate.parse(date).plusWeeks(x).toString()
                    )
                    else -> addEventWithAnotherDate(
                        LocalDate.parse(date).plusMonths(x).toString()
                    )
                }
            }
        }

        val action = CreateEventFragmentDirections.actionCreateEventFragmentToNavigationCalendar()
        fragment.findNavController().navigate(action)
    }

    fun updateEvent() {
        fragment.apply {
            currentEvent.title = title
            currentEvent.location = location
            currentEvent.group = group
            currentEvent.date = date
            currentEvent.time_from =
                if (binding.allDay.isChecked) getString(R.string.all_day) else timeFrom
            currentEvent.time_to = if (binding.allDay.isChecked) "" else timeTo
            currentEvent.url = url
            currentEvent.notes = notes

            scheduleEventShareViewModel.updateItem(currentEvent)
            findNavController().navigateUp()
        }
    }
}