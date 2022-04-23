package ru.nsu.fit.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


private const val LOCALE = "ru"
private const val CUSTOMER_LIST_DATE_FORMAT = "MMMM"

fun calendarToStringFormat(calendar: Calendar): String = with(calendar) {
    DateTimeFormatter.ofPattern(CUSTOMER_LIST_DATE_FORMAT)
        .withLocale(Locale(LOCALE))
        .format(
            LocalDate.of(
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH)
            )
        )
}

fun Calendar.toStringFormat(): String = calendarToStringFormat(this)
