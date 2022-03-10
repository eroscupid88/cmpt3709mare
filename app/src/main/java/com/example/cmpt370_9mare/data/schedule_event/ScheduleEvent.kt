package com.example.cmpt370_9mare.data.schedule_event

import android.content.ClipData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat


@Entity(tableName = "event")
data class ScheduleEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "date_from")
    val date_from: String,
    @ColumnInfo(name = "date_to")
    val date_to: String,
    @ColumnInfo(name = "time_from")
    val time_from: String,
    @ColumnInfo(name = "time_to")
    val time_to: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "notes")
    val notes: String
)

/**
 * Returns the passed in price in currency format.
 */
fun ScheduleEvent.getFormattedTime(dayFrom: String, timeFrom: String): String =
    "At $dayFrom, $timeFrom"