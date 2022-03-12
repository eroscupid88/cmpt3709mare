package com.example.cmpt370_9mare

import androidx.lifecycle.*
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEventDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ScheduleEventViewModel(private val scheduleEventDao: ScheduleEventDao) : ViewModel() {

    // Cache all events form the database using LiveData.
    val allEvents: LiveData<List<ScheduleEvent>> = scheduleEventDao.getAllEvents().asLiveData()

    /**
     * insertEvent function insert new event into EventRoomDatabase
     */
    private fun insertEvent(scheduleEvent: ScheduleEvent) {
        viewModelScope.launch {
            scheduleEventDao.insertEvent(scheduleEvent)
        }
    }

    /**
     * private function getNewItemEntry take variables and return new ScheduleEvent
     */
    private fun getNewItemEntry(
        title: String,
        location: String,
        date: String,
        time_from: String,
        time_to: String,
        url: String,
        notes: String
    ): ScheduleEvent {
        return ScheduleEvent(
            title = title,
            location = location,
            date = date,
            time_from = time_from,
            time_to = time_to,
            url = url,
            notes = notes
        )
    }

    /**
     * public function create new item and insert ScheduleEvent into EventRoomDatabase
     */

    fun addNewItem(
        title: String,
        location: String,
        date: String,
        time_from: String,
        time_to: String,
        url: String,
        notes: String
    ) {
        val newItem =
            getNewItemEntry(title, location, date, time_from, time_to, url, notes)
        insertEvent(newItem)
    }

    /**
     * public function isEntryValid check whether or not title input is empty or not.
     * event created if only title is not blank
     */
    fun isEntryValid(title: String): Boolean {
        if (title.isBlank()) {
            return false
        }
        return true
    }

    fun eventFromId(id: Int): LiveData<ScheduleEvent> =
        scheduleEventDao.getScheduleEvent(id).asLiveData()
}

/**
 * Boilerplate code to create Singleton ScheduleEventViewModel
 */
class ScheduleEventViewModelFactory(private val scheduleEventDao: ScheduleEventDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleEventViewModel(scheduleEventDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}