package com.example.wateringreminder.extensions

import com.example.wateringreminder.utils.constants.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDate.toWateringDay(): String {
    val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern(DateFormat.FORMATTER).withLocale(Locale.ENGLISH)
    return if (isToday()) {
        "WATER DAY"
    } else {
        format(formatter)
    }
}

fun LocalDate.isToday(): Boolean {
    return isEqual(LocalDate.now())
}