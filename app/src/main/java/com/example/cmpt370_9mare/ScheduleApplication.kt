package com.example.cmpt370_9mare

import android.app.Application
import com.example.cmpt370_9mare.data.EventRoomDatabase

class ScheduleApplication: Application() {
    val database: EventRoomDatabase by lazy { EventRoomDatabase.getDatabase(this)}

}