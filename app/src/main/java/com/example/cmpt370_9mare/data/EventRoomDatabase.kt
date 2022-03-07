package com.example.cmpt370_9mare.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EventRoomDatabase::class],version = 1,exportSchema = false)
abstract class EventRoomDatabase: RoomDatabase() {

    abstract fun scheduleEventDao() : ScheduleEventDao

    companion object {
        @Volatile
        private var INSTANCE : EventRoomDatabase? = null
        fun getDatabase(context:Context) : EventRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventRoomDatabase::class.java,
                    "event_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}