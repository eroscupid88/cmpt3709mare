package com.example.cmpt370_9mare.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEventDao

class DashboardViewModel(private val eventDao: ScheduleEventDao) : ViewModel() {

    // Cache all events form the database using LiveData.
    val allEvents: LiveData<List<ScheduleEvent>> = eventDao.getAllEvents().asLiveData()
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class DashboardViewModelFactory(private val eventDao: ScheduleEventDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(eventDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}