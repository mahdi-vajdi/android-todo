package com.mahdivajdi.simpletodo.domain.model

data class Task(
    val taskId: Long = 0,
    val taskCategoryId: Long,
    val title: String,
    val description: String,
    val timestamp: Long,
    val done: Boolean = false,
)


