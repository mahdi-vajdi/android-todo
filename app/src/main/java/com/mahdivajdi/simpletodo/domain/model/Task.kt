package com.mahdivajdi.simpletodo.domain.model

data class Task(
    val taskId: Long = 0,
    val taskCategoryId: Long,
    var title: String,
    var description: String,
    val timestamp: Long,
    var done: Boolean = false,
)


