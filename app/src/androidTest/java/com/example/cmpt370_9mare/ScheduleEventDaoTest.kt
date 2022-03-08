package com.example.cmpt370_9mare

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cmpt370_9mare.data.EventRoomDatabase
import com.example.cmpt370_9mare.data.ScheduleEvent
import com.example.cmpt370_9mare.data.ScheduleEventDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RunWith(AndroidJUnit4::class)
class ScheduleEventDaoTest {
    private lateinit var scheduleEventDao: ScheduleEventDao
    private lateinit var db: EventRoomDatabase
    private val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)

    private fun convertDate(someDate:String): LocalDate? {
        return LocalDate.parse(someDate, formatter)
    }

    // create database
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, EventRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        scheduleEventDao = db.scheduleEventDao()
    }

    //close database
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * create first Event
     */
    @Test
    @Throws(IOException::class)
    fun insertAndGetScheduleEvent():Unit = runBlocking {
        val scheduleEvent = ScheduleEvent(1,"event1","Saskatoon","July 25, 2017","July 25, 2017","10:40 AM","10:50 AM","","event1 testing notes")
        scheduleEventDao.insertEvent(scheduleEvent)
        val events:List<ScheduleEvent> = scheduleEventDao.getScheduleEvents().first()
        assertEquals(events[0].id,scheduleEvent.id)
        assertEquals(events[0].date_from,scheduleEvent.date_from)
        assertEquals(events[0].date_to,scheduleEvent.date_to)
        assertEquals(events[0].time_from,scheduleEvent.time_from)
        assertEquals(events[0].time_to,scheduleEvent.time_to)
        assertEquals(events[0].location,scheduleEvent.location)
        assertEquals(events[0].url,scheduleEvent.url)
        assertEquals(events[0].notes,scheduleEvent.notes)
    }

}