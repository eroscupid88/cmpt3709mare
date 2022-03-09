package com.example.cmpt370_9mare.data.schedule_event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "event")
data class ScheduleEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "date_from")
    val date_from: String,
    @ColumnInfo(name = "date_to")
    val date_to: String,
    @ColumnInfo(name= "time_from")
    val time_from: String,
    @ColumnInfo(name = "time_to")
    val time_to: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "notes")
    val notes: String
)