package com.mahdivajdi.simpletodo.ui

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

// Date Related
fun timeStampToDate(timeStamp: Long): String {
    val dateTime = LocalDateTime.ofEpochSecond(timeStamp, 0, ZonedDateTime.now().offset)
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM d")
    return dateTime.format(formatter)
}

fun dueDateString(timeStamp: Long): String? {
    if (timeStamp == 0L) return null

    val today = LocalDate.now()
    val dueDate = LocalDate.ofEpochDay(timeStamp)
    val periodDays = ChronoUnit.DAYS.between(today, dueDate)

    return if (periodDays <= -14L) {
        val weeks = ChronoUnit.WEEKS.between(today, dueDate)
        "Due ${kotlin.math.abs(weeks)} weeks ago"
    } else if (periodDays in -13L..-7L) "Due 1 week ago"
    else if (periodDays in -6L..-2L) "Due ${kotlin.math.abs(periodDays)} days ago"
    else if (periodDays == -1L) "Due Yesterday"
    else if (periodDays == 0L) "Due Today"
    else if (periodDays == 1L) "Due Tomorrow"
    else {
        val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d")
        "Due ${dueDate.format(dateFormatter)}"
    }
}

/*
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Task>?) {
    Log.d("list_delay", "list data binding adapter function called ")
    val adapter = recyclerView.adapter as TaskListAdapter
    adapter.submitList(data)
}*/
