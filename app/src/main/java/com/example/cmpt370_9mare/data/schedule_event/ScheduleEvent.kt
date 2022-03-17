package com.example.cmpt370_9mare.data.schedule_event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "event")
data class ScheduleEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "location")
    var location: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "time_from")
    var time_from: String,
    @ColumnInfo(name = "time_to")
    var time_to: String,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "notes")
    var notes: String
)

/**
 * Returns the passed in price in currency format.
 */
fun getFormattedTime(dayFrom: String, timeFrom: String): String =
    "At $dayFrom, $timeFrom"