package com.example.cmpt370_9mare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cmpt370_9mare.data.ScheduleEventDao

class ScheduleEventViewModel(private val scheduleEventDao: ScheduleEventDao): ViewModel() {
}

class ScheduleEventViewModelFactory(private val scheduleEventDao: ScheduleEventDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleEventViewModel(scheduleEventDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}