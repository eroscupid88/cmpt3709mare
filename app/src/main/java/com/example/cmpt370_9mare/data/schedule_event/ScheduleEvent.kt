package com.example.cmpt370_9mare.data.schedule_event

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "event")
data class ScheduleEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "date")
    val date: String?,
    @ColumnInfo(name = "time_from")
    var time_from: String,
    @ColumnInfo(name = "time_to")
    var time_to: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "notes")
    val notes: String
)

/**
 * Returns the passed in price in currency format.
 */
fun getFormattedTime(dayFrom: String, timeFrom: String): String =
    "At $dayFrom, $timeFrom"