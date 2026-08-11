package co.tz.pesalock.voni.dates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.datetime.*
import kotlin.time.Clock

class CalendarState(
    initialDate: LocalDate? = null,
    initialEndDate: LocalDate? = null,
    val selectionMode: SelectionMode = SelectionMode.Range
) {
    var selectedStart by mutableStateOf(initialDate)
    var selectedEnd by mutableStateOf(initialEndDate)
    var currentMonth by mutableStateOf(
        LocalDate(
            (initialDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())).year,
            (initialDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())).month,
            1
        )
    )

    fun onDateClick(date: LocalDate) {
        when (selectionMode) {
            SelectionMode.Single -> {
                selectedStart = date
                selectedEnd = null
            }
            SelectionMode.Range -> {
                if (selectedStart == null || (selectedStart != null && selectedEnd != null)) {
                    selectedStart = date
                    selectedEnd = null
                } else {
                    val start = selectedStart!!
                    if (date < start) {
                        selectedEnd = start
                        selectedStart = date
                    } else if (date == start) {
                        selectedStart = null
                    } else {
                        selectedEnd = date
                    }
                }
            }
        }
    }

    fun nextMonth() {
        currentMonth = currentMonth.plusMonths(1)
    }

    fun prevMonth() {
        currentMonth = currentMonth.minusMonths(1)
    }
    
    fun reset(start: LocalDate?, end: LocalDate?) {
        selectedStart = start
        selectedEnd = end
        currentMonth = LocalDate(
            (start ?: Clock.System.todayIn(TimeZone.currentSystemDefault())).year,
            (start ?: Clock.System.todayIn(TimeZone.currentSystemDefault())).month,
            1
        )
    }

    enum class SelectionMode { Single, Range }
}

@Composable
fun rememberCalendarState(
    initialDate: LocalDate? = null,
    initialEndDate: LocalDate? = null,
    selectionMode: CalendarState.SelectionMode = CalendarState.SelectionMode.Range
): CalendarState = remember {
    CalendarState(initialDate, initialEndDate, selectionMode)
}
