package co.tz.pesalock.voni.dates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate

@Composable
internal fun CalendarDay(
    date: LocalDate,
    isSelected: Boolean,
    isInRange: Boolean,
    colors: CalendarColors,
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier
        .fillMaxSize()
        .padding(2.dp)
        .clip(CircleShape)
        .background(
            when {
                isSelected -> colors.primary
                isInRange -> colors.rangeBackground
                else -> Color.Transparent
            }
        ),
    contentAlignment = Alignment.Center
) {
    Text(
        text = date.day.toString(),
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
        color = when {
            isSelected -> colors.onPrimary
            else -> colors.onSurface.copy(alpha = 0.8f)
        },
        lineHeight = 1.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
