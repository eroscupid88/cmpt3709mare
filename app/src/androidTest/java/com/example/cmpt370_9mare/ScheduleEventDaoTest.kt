package com.example.cmpt370_9mare

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cmpt370_9mare.data.schedule_event.EventRoomDatabase
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEventDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
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
     * create first Event with full input
     */
    @Test
    @Throws(IOException::class)
    fun insertAndGetScheduleEventTest1():Unit = runBlocking {
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

    /**
     * create first Event with only title
     */
    @Test
    @Throws(IOException::class)
    fun insertAndGetScheduleEventTest2():Unit = runBlocking {
        val scheduleEvent = ScheduleEvent(2,"event2","","","","","","","")
        scheduleEventDao.insertEvent(scheduleEvent)
        val events:List<ScheduleEvent> = scheduleEventDao.getScheduleEvents().first()
        assertEquals(2,scheduleEvent.id)
        assertEquals(events[0].title,scheduleEvent.title)
        assertEquals("",scheduleEvent.date_from)
        assertEquals("",scheduleEvent.date_to)
        assertEquals("",scheduleEvent.time_from)
        assertEquals("",scheduleEvent.time_to)
        assertEquals("",scheduleEvent.location)
        assertEquals("",scheduleEvent.url)
        assertEquals("",scheduleEvent.notes)
    }


    /**
     * Test size of the even after created 3 events
     */
    @Test
    @Throws(IOException::class)
    fun getScheduleEventsTest():Unit = runBlocking {
        val scheduleEvent1 = ScheduleEvent(1,"event1","","","","","","","")
        val scheduleEvent2 = ScheduleEvent(2,"event2","","","","","","","")
        val scheduleEvent3 = ScheduleEvent(3,"event3","","","","","","","")
        scheduleEventDao.insertEvent(scheduleEvent1)
        scheduleEventDao.insertEvent(scheduleEvent2)
        scheduleEventDao.insertEvent(scheduleEvent3)
        assertEquals(3,scheduleEventDao.getScheduleEvents().first().size)
        assertEquals(scheduleEvent2.title,scheduleEventDao.getScheduleEvent(2).first().title)
    }


    /**
     * Test getScheduleEvent
     */
    @Test
    @Throws(IOException::class)
    fun getScheduleEventTest1():Unit = runBlocking {
        val scheduleEvent1 = ScheduleEvent(1,"event1","","","","","","","")
        scheduleEventDao.insertEvent(scheduleEvent1)
        assertEquals(scheduleEvent1.title,scheduleEventDao.getScheduleEvent(1).first().title)
    }


    /**
     * Test getScheduleEvent
     * create 2 events and check 2nd event
     */
    @Test
    @Throws(IOException::class)
    fun getScheduleEventTest2():Unit = runBlocking {
        val scheduleEvent1 = ScheduleEvent(1,"event1","","","","","","","")
        val scheduleEvent2 = ScheduleEvent(2,"event2","","","","10:20 AM","","","")
        scheduleEventDao.insertEvent(scheduleEvent1)
        scheduleEventDao.insertEvent(scheduleEvent2)
        assertEquals(scheduleEvent2.time_from,scheduleEventDao.getScheduleEvent(2).first().time_from)
    }


//    /**
//     * Test updateEvent
//     * create 2 events and check 2nd event
//     */
//    @Test
//    @Throws(IOException::class)
//    fun updateEventTest():Unit = runBlocking {
//        val scheduleEvent1 = ScheduleEvent(1,"event1","","Mar 18,2022","Mar 20,2022","","","","")
//        scheduleEventDao.insertEvent(scheduleEvent1)
//        assertEquals(scheduleEvent1.date_from,scheduleEventDao.getScheduleEvent(2).first().date_from)
//        assertEquals(scheduleEvent1.date_to,scheduleEventDao.getScheduleEvent(2).first().date_to)
//        val newScheduleEvent1 = ScheduleEvent(1,"event1","","Mar 20,2022","Mar 21,2022","10:00 AM","11:00 AM","","")
//        scheduleEventDao.updateEvent(newScheduleEvent1)
//        assertEquals("Mar 21,2022",scheduleEventDao.getScheduleEvent(2).first().date_to)
//        assertEquals("11:00 AM",scheduleEventDao.getScheduleEvent(2).first().time_to)


//    }


}