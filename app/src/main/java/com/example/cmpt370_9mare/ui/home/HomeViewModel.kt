package com.example.cmpt370_9mare.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "HomeViewModel"

@RequiresApi(Build.VERSION_CODES.O)

class HomeViewModel() : ViewModel() {


    private var _currentDay = MutableLiveData<String>()
    var currentDay: LiveData<String> = _currentDay

    private var today: LocalDate = LocalDate.now()

    init {
        _currentDay.value = stringFormatToday(today)


    }

    private fun stringFormatToday(today: LocalDate): String {
        return today.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
    }

    fun getToday():String{
        return stringFormatToday(today)
    }
    fun getPreviousDay() {
        today = today.plusDays(-1)
        _currentDay.value = stringFormatToday(today)
    }

    fun getNextDay() {
        today = today.plusDays(1)
        _currentDay.value = stringFormatToday(today)
    }
}
