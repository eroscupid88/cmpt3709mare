package ca.nomosnow.cmpt370_9mare.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)

class HomeViewModel : ViewModel() {


    private var _currentDay = MutableLiveData<String>()

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
}
