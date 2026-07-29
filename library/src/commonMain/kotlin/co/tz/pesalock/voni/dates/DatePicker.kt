//package co.tz.pesalock.voni.dates
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ChevronLeft
//import androidx.compose.material.icons.filled.ChevronRight
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//import kotlinx.datetime.*
//import kotlin.time.Clock
//
///**
// * A customizable date range picker that allows selecting a start and end date.
// * Features:
// * - Single date selection or range selection.
// * - Month navigation.
// * - Customizable colors.
// * - Selects current date by default.
// */
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DateRangePicker(
//    modifier: Modifier = Modifier,
//    label: String = "Select Date Range",
//    startDate: LocalDate? = null,
//    endDate: LocalDate? = null,
//    colors: DatePickerColors = DatePickerDefaults.colors(),
//    onRangeSelected: (LocalDate?, LocalDate?) -> Unit
//) {
//    var showDialog by remember { mutableStateOf(false) }
//    val displayText = remember(startDate, endDate) {
//        when {
//            startDate != null && endDate != null -> "${startDate.format()} - ${endDate.format()}"
//            startDate != null -> startDate.format()
//            else -> ""
//        }
//    }
//
//    OutlinedTextField(
//        value = displayText,
//        onValueChange = {},
//        modifier = modifier.fillMaxWidth(),
//        readOnly = true,
//        label = { Text(label) },
//        trailingIcon = {
//            IconButton(onClick = { showDialog = true }) {
//                Icon(Icons.Default.DateRange, contentDescription = "Open Date Picker")
//            }
//        },
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = colors.accentColor,
//            focusedLabelColor = colors.accentColor
//        )
//    )
//
//    if (showDialog) {
//        DateRangePickerDialog(
//            initialStartDate = startDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault()),
//            initialEndDate = endDate,
//            colors = colors,
//            onDismiss = { showDialog = false },
//            onSelected = { start, end ->
//                onRangeSelected(start, end)
//                showDialog = false
//            }
//        )
//    }
//}
//
//@Composable
//fun DateRangePickerDialog(
//    initialStartDate: LocalDate,
//    initialEndDate: LocalDate?,
//    colors: DatePickerColors,
//    onDismiss: () -> Unit,
//    onSelected: (LocalDate?, LocalDate?) -> Unit
//) {
//    var currentMonth by remember { mutableStateOf(LocalDate(initialStartDate.year, initialStartDate.month, 1)) }
//    var selectedStartDate by remember { mutableStateOf<LocalDate?>(initialStartDate) }
//    var selectedEndDate by remember { mutableStateOf<LocalDate?>(initialEndDate) }
//
//    Dialog(onDismissRequest = onDismiss) {
//        Surface(
//            shape = RoundedCornerShape(16.dp),
//            color = colors.containerColor,
//            tonalElevation = 4.dp
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .width(IntrinsicSize.Min)
//            ) {
//                // Header: Month Selection
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
//                        Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month", tint = colors.contentColor)
//                    }
//                    Text(
//                        text = "${currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${currentMonth.year}",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = colors.contentColor,
//                        fontWeight = FontWeight.Bold
//                    )
//                    IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
//                        Icon(Icons.Default.ChevronRight, contentDescription = "Next Month", tint = colors.contentColor)
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Days of the Week Header
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
//                    daysOfWeek.forEach { day ->
//                        Text(
//                            text = day,
//                            modifier = Modifier.weight(1f),
//                            textAlign = TextAlign.Center,
//                            style = MaterialTheme.typography.labelMedium,
//                            color = colors.contentColor.copy(alpha = 0.6f)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Calendar Grid
//                val daysInMonth = currentMonth.daysInMonth()
//                val firstDayOfWeek = LocalDate(currentMonth.year, currentMonth.month, 1).dayOfWeek.ordinal // 0=Mon...6=Sun
//                val offset = (firstDayOfWeek + 1) % 7
//
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(7),
//                    modifier = Modifier.height(240.dp)
//                ) {
//                    // Empty slots for the first week
//                    items(offset) { Spacer(modifier = Modifier.fillMaxSize()) }
//
//                    items(daysInMonth) { dayIndex ->
//                        val day = dayIndex + 1
//                        val date = LocalDate(currentMonth.year, currentMonth.month, day)
//                        val isSelected = date == selectedStartDate || date == selectedEndDate
//                        val isInRange = selectedStartDate != null && selectedEndDate != null &&
//                                date > selectedStartDate!! && date < selectedEndDate!!
//
//                        Box(
//                            modifier = Modifier
//                                .aspectRatio(1f)
//                                .padding(2.dp)
//                                .clip(CircleShape)
//                                .background(
//                                    when {
//                                        isSelected -> colors.accentColor
//                                        isInRange -> colors.accentColor.copy(alpha = 0.2f)
//                                        else -> Color.Transparent
//                                    }
//                                )
//                                .clickable {
//                                    if (selectedStartDate == null || (selectedStartDate != null && selectedEndDate != null)) {
//                                        selectedStartDate = date
//                                        selectedEndDate = null
//                                    } else {
//                                        if (date < selectedStartDate!!) {
//                                            selectedEndDate = selectedStartDate
//                                            selectedStartDate = date
//                                        } else {
//                                            selectedEndDate = date
//                                        }
//                                    }
//                                },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = day.toString(),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = if (isSelected) colors.onAccentColor else colors.contentColor,
//                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Footer: Actions
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    TextButton(onClick = onDismiss) {
//                        Text("Cancel", color = colors.accentColor)
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(
//                        onClick = { onSelected(selectedStartDate, selectedEndDate) },
//                        colors = ButtonDefaults.buttonColors(containerColor = colors.accentColor)
//                    ) {
//                        Text("Select", color = colors.onAccentColor)
//                    }
//                }
//            }
//        }
//    }
//}
//
//// Data Class for Custom Colors
//data class DatePickerColors(
//    val containerColor: Color,
//    val contentColor: Color,
//    val accentColor: Color,
//    val onAccentColor: Color
//)
//
//object DatePickerDefaults {
//    @Composable
//    fun colors(
//        containerColor: Color = MaterialTheme.colorScheme.surface,
//        contentColor: Color = MaterialTheme.colorScheme.onSurface,
//        accentColor: Color = MaterialTheme.colorScheme.primary,
//        onAccentColor: Color = MaterialTheme.colorScheme.onPrimary
//    ) = DatePickerColors(containerColor, contentColor, accentColor, onAccentColor)
//}
//
//// Helper Extensions
//fun LocalDate.format(): String = "${dayOfMonth.toString().padStart(2, '0')}/${month.number.toString().padStart(2, '0')}/$year"
//
//fun LocalDate.minusMonths(months: Int): LocalDate {
//    var newMonth = month.number - months
//    var newYear = year
//    while (newMonth < 1) {
//        newMonth += 12
//        newYear -= 1
//    }
//    return LocalDate(newYear, newMonth, 1)
//}
//
//fun LocalDate.plusMonths(months: Int): LocalDate {
//    var newMonth = month.number + months
//    var newYear = year
//    while (newMonth > 12) {
//        newMonth -= 12
//        newYear += 1
//    }
//    return LocalDate(newYear, newMonth, 1)
//}
//
//fun LocalDate.daysInMonth(): Int {
//    return when (month) {
//        Month.FEBRUARY -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
//        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
//        else -> 31
//    }
//}
