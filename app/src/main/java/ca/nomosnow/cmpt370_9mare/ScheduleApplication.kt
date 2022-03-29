package ca.nomosnow.cmpt370_9mare

import android.app.Application
import ca.nomosnow.cmpt370_9mare.data.schedule_event.EventRoomDatabase

class ScheduleApplication : Application() {
    val database: EventRoomDatabase by lazy { EventRoomDatabase.getDatabase(this) }
}