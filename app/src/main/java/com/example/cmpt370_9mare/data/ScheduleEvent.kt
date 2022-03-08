package com.example.cmpt370_9mare.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "event")
data class ScheduleEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "date")
    val date: LocalDate?,
    @ColumnInfo(name= "from")
    val from: String,
    @ColumnInfo(name = "to")
    val to: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "notes")
    val notes: String
)