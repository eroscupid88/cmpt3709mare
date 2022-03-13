package com.example.cmpt370_9mare

import androidx.lifecycle.*
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEventDao
import kotlinx.coroutines.launch
import java.util.*

class ScheduleEventViewModel(private val scheduleEventDao: ScheduleEventDao) : ViewModel() {

    // Cache all events form the database using LiveData.
    val allEvents: LiveData<List<ScheduleEvent>> = scheduleEventDao.getAllEvents().asLiveData()

    // Cache future/past events from the database by comparing with current date
    val futureEvents: LiveData<List<ScheduleEvent>> =
        scheduleEventDao.getFutureEvents(getCurrentDate()).asLiveData()
    val pastEvent: LiveData<List<ScheduleEvent>> =
        scheduleEventDao.getPastEvents(getCurrentDate()).asLiveData()

    // Searched Events
    lateinit var searchedEvents: LiveData<List<ScheduleEvent>>

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

    private fun getCurrentDate(): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)

        return String.format("$year-%02d-%02d", month, day)
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

    fun updateItem(
        event: ScheduleEvent
    ) {
        updateEvent(event)
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

    fun searchEvent(name: String) {
        searchedEvents = scheduleEventDao.searchEventByName(name).asLiveData()
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