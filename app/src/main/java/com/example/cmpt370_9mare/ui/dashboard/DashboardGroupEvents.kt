package com.example.cmpt370_9mare.ui.dashboard

import androidx.lifecycle.LiveData
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent

data class DashboardGroupEvents(
    val date: String,
    val events: LiveData<List<ScheduleEvent>>
)