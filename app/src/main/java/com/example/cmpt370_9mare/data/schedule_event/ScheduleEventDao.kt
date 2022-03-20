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

    @Query("SELECT * FROM event WHERE date < :currentDate ORDER by date DESC")
    fun getPastEvents(currentDate: String): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event ORDER BY id")
    fun getAllEvents(): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event WHERE date = :date ORDER BY time_from")
    fun getEventByDate(date: String): Flow<List<ScheduleEvent>>

    @Query(
        "SELECT * FROM event WHERE date = :date AND NOT id = :eventId AND ((:timeTo BETWEEN time_from AND time_to)" +
                "OR (:timeFrom BETWEEN time_from AND time_to) OR (time_from BETWEEN :timeFrom AND :timeTo) OR" +
                " (time_to BETWEEN :timeFrom AND :timeTo))"
    )
    fun getConflictEvent(
        date: String,
        timeFrom: String,
        timeTo: String,
        eventId: Int
    ): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event WHERE time_to >= :currentTime AND date = :date ORDER BY time_from")
    fun getDailyEventByTimeAndDate(currentTime: String, date: String): Flow<List<ScheduleEvent>>

    @Query("SELECT * FROM event WHERE title LIKE :name ORDER by date")
    fun searchEventByName(name: String): Flow<List<ScheduleEvent>>


}