package co.tz.pesalock.voni.dates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DayOfWeek

@Composable
internal fun MonthNavigationHeader(
    state: CalendarState,
    colors: CalendarColors,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    IconButton(
        onClick = {
            state.prevMonth()
        }
    ) {
        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = "Prev",
            tint = colors.onSurface
        )
    }
    Text(
        text = "${state.currentMonth.month.name} ${state.currentMonth.year}",
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
        color = colors.onSurface.copy(alpha = 0.8f),
        lineHeight = 1.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    IconButton(
        onClick = {
            state.nextMonth()
        }
    ) {
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Next",
            tint = colors.onSurface
        )
    }
}

@Composable
internal fun WeekdaysHeader(
    colors: CalendarColors,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier.fillMaxWidth()
) {
    val days = listOf(
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY
    )
    days.forEach { day ->
        Text(
            text = dayLabel(day),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 15.sp),
            color = colors.textDim.copy(alpha = 0.8f),
            lineHeight = 1.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
