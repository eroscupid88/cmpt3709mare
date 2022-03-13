package com.example.cmpt370_9mare.data

import androidx.annotation.Nullable
import java.time.LocalDate
import java.time.YearMonth

//just change
data class Day(
    @Nullable
    val day: String?,
    val date: LocalDate?
    )