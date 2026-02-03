package com.example.week1.view

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val finnishDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd-MM-yyyy")

fun LocalDate.toFinnishDate(): String {
    return this.format(finnishDateFormatter)
}
