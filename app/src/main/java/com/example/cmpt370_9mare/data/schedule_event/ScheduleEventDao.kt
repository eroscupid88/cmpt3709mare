package com.example.cmpt370_9mare.data.schedule_event

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleEventDao {
    // ignore the conflict for now
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(scheduleEvent: ScheduleEvent)

    @Update
    suspend fun updateEvent(scheduleEvent: ScheduleEvent)

    @Delete
    suspend fun deleteEvent(scheduleEvent: ScheduleEvent)

    @Query("SELECT * FROM event WHERE id = :id")
    fun getScheduleEvent(id: Int): Flow<ScheduleEvent>

    @Query("SELECT * FROM event ORDER BY title ASC")
    fun getScheduleEvents(): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event WHERE date >= :currentDate ORDER by date")
    fun getFutureEvents(currentDate: String): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event WHERE date < :currentDate ORDER by date")
    fun getPastEvents(currentDate: String): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event ORDER BY id")
    fun getAllEvents(): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event WHERE date = :date ORDER BY time_from")
    fun getEventByDate(date: String): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event WHERE date = :date AND ((:timeFrom <= time_from AND time_from < :timeTo) OR (:timeFrom < time_to AND time_to <= :timeTo))")
    fun getEventByDateTime(date: String, timeFrom: String, timeTo: String): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event WHERE title LIKE :name ORDER by date")
    fun searchEventByName(name: String): Flow<List<ScheduleEvent>>
}