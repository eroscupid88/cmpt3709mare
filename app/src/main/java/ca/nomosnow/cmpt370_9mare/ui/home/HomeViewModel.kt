package ca.nomosnow.cmpt370_9mare.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import ca.nomosnow.cmpt370_9mare.data.schedule_event.ScheduleEventDao
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "HomeViewModel"

@RequiresApi(Build.VERSION_CODES.O)

class HomeViewModel() : ViewModel() {


    private var _currentDay = MutableLiveData<String>()
    var currentDay: LiveData<String> = _currentDay

    private var today: LocalDate = LocalDate.now()

    init {
        _currentDay.value = stringDisplayToday(today)


    }

    private fun stringFormatToday(today: LocalDate): String {
        return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    private fun stringDisplayToday(today: LocalDate): String {
        return today.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
    }

    fun getToday(): String {
        return stringFormatToday(today)
    }

    fun monthDisplay() {
        today = today.plusDays(-1)
        _currentDay.value = stringDisplayToday(today)
    }

    fun dayDisplay() {
        today = today.plusDays(1)
        _currentDay.value = stringDisplayToday(today)
    }
}
