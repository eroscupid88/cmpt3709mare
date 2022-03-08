package com.example.cmpt370_9mare.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleEventDao  {
    // ignore the conflict for now
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(scheduleEvent: ScheduleEvent)
    @Update
    suspend fun updateEvent(scheduleEvent: ScheduleEvent)
    @Delete
    suspend fun deleteEvent(scheduleEvent: ScheduleEvent)
    @Query("SELECT * FROM event WHERE id = :id")
    fun getScheduleEvent(id: Int) : Flow<ScheduleEvent>
    @Query("SELECT * FROM event ORDER BY title ASC")
    fun getScheduleEvents(): Flow<List<ScheduleEvent>>

}