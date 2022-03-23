package com.example.cmpt370_9mare.data

import androidx.annotation.Nullable
import java.time.LocalDate

//just change
data class Day(
    @Nullable
    val day: String?,
    val date: LocalDate?,
    val selected: Boolean
    )