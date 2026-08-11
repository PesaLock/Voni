package co.tz.pesalock.voni.dates

import kotlinx.datetime.*

internal fun LocalDate.minusMonths(n: Int): LocalDate {
    var m = month.number - n
    var y = year
    while (m < 1) {
        m += 12
        y -= 1
    }
    return LocalDate(y, m, 1)
}

internal fun LocalDate.plusMonths(n: Int): LocalDate {
    var m = month.number + n
    var y = year
    while (m > 12) {
        m -= 12
        y += 1
    }
    return LocalDate(y, m, 1)
}

internal fun LocalDate.daysInMonth(): Int = when (month) {
    Month.FEBRUARY -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
    Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
    else -> 31
}

internal fun dayLabel(day: DayOfWeek): String = when (day) {
    DayOfWeek.MONDAY -> "M"; DayOfWeek.TUESDAY -> "T"; DayOfWeek.WEDNESDAY -> "W"
    DayOfWeek.THURSDAY -> "T"; DayOfWeek.FRIDAY -> "F"; DayOfWeek.SATURDAY -> "S"; DayOfWeek.SUNDAY -> "S"
}
