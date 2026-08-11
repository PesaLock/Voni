package co.tz.pesalock.voni.dates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate

@Composable
fun InteractiveCalendar(
    state: CalendarState,
    modifier: Modifier = Modifier,
    colors: CalendarColors = InteractiveCalendarDefaults.colors(),
    showMonthNavigation: Boolean = true,
    dayContent: @Composable (LocalDate, Boolean, Boolean) -> Unit = { date, isSelected, isInRange ->
        CalendarDay(
            date = date,
            isSelected = isSelected,
            isInRange = isInRange,
            colors = colors
        )
    }
) = Column(
    modifier = modifier
) {
    when {
        showMonthNavigation -> MonthNavigationHeader(
            state = state,
            colors = colors
        )
        else -> {}
    }

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    WeekdaysHeader(
        colors = colors
    )

    Spacer(
        modifier = Modifier.height(8.dp)
    )

    CalendarGrid(
        state = state,
        dayContent = dayContent
    )
}
