package com.example.cmpt370_9mare

import androidx.lifecycle.*
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEventDao
import com.example.cmpt370_9mare.data.schedule_event.getCurrentDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ScheduleEventViewModel(private val scheduleEventDao: ScheduleEventDao) : ViewModel() {
    val today = getCurrentDate()
    // Cache all events form the database using LiveData.
    val allEvents: LiveData<List<ScheduleEvent>> = scheduleEventDao.getAllEvents().asLiveData()
    val TodayAndFutureEvents: LiveData<List<ScheduleEvent>> = scheduleEventDao.getTodayAndFutureEvent(today).asLiveData()
    // Cache future/past events from the database by comparing with current date
    //val today = getCurrentDate()


    val pickedDate = MutableLiveData<String>()
    val pickedTimeFrom = MutableLiveData<String>()
    val pickedTimeTo = MutableLiveData<String>()

    /**
     * insertEvent function insert new event into EventRoomDatabase
     */
    private fun insertEvent(scheduleEvent: ScheduleEvent) {
        viewModelScope.launch {
            scheduleEventDao.insertEvent(scheduleEvent)
        }
    }

    /**
     * insertEvent function insert new event into EventRoomDatabase
     */
    private fun updateEvent(scheduleEvent: ScheduleEvent) {
        viewModelScope.launch {
            scheduleEventDao.updateEvent(scheduleEvent)
        }
    }

    /**
     * insertEvent function insert new event into EventRoomDatabase
     */
    private fun deleteEvent(scheduleEvent: ScheduleEvent) {
        viewModelScope.launch {
            scheduleEventDao.deleteEvent(scheduleEvent)
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
     * Update event function
     */
    fun updateItem(
        event: ScheduleEvent
    ) {
        updateEvent(event)
    }


    /**
     *  DeleteEvent
     */
    fun deleteItem(event: ScheduleEvent) {
        deleteEvent(event)
    }

    /**
     * Return an event which has the given ID
     */
    fun eventFromId(id: Int): LiveData<ScheduleEvent> =
        scheduleEventDao.getScheduleEvent(id).asLiveData()

    fun eventFromDate(date: String): LiveData<List<ScheduleEvent>> {
        return scheduleEventDao.getEventByDate(date).asLiveData()
    }

    fun pickDate(date: String) {
        pickedDate.value = date
    }

    fun pickTimeFrom(time: String) {
        pickedTimeFrom.value = time
    }

    fun pickTimeTo(time: String) {
        pickedTimeTo.value = time
    }

    fun eventConflicts(
        date: String,
        timeFrom: String,
        timeTo: String,
        eventId: Int
    ): Flow<List<ScheduleEvent>> {
        return scheduleEventDao.getConflictEvent(date, timeFrom, timeTo, eventId)
    }
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