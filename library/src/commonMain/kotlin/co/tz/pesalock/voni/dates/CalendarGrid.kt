package co.tz.pesalock.voni.dates

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate

@Composable
internal fun CalendarGrid(
    state: CalendarState,
    modifier: Modifier = Modifier,
    dayContent: @Composable (LocalDate, Boolean, Boolean) -> Unit
) = LazyVerticalGrid(
    columns = GridCells.Fixed(7),
    modifier = modifier.height(240.dp)
) {
    val daysInMonth = state.currentMonth.daysInMonth()
    val firstDayOfMonth = LocalDate(
        year = state.currentMonth.year,
        month = state.currentMonth.month,
        day = 1
    )
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal // 0=Mon, 6=Sun
    val offset = (firstDayOfWeek + 1) % 7 // Adjusted for Sunday start

    items(offset) {
        Spacer(
            modifier = Modifier.fillMaxSize()
        )
    }
    items(daysInMonth) { index ->
        val date = LocalDate(
            year = state.currentMonth.year,
            month = state.currentMonth.month,
            day = index + 1
        )
        val isSelected = date == state.selectedStart || date == state.selectedEnd
        val isInRange = state.selectedStart != null && state.selectedEnd != null && 
            date > state.selectedStart!! && date < state.selectedEnd!!
        
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable {
                    state.onDateClick(date)
                },
            contentAlignment = Alignment.Center
        ) {
            dayContent(date, isSelected, isInRange)
        }
    }
}
