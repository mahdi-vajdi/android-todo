package com.mahdivajdi.simpletodo.domain.model

data class Task(
    val taskId: Long = 0,
    val taskCategoryId: Long,
    val title: String,
    val detail: String,
    val dateModified: Long,
    val state: Boolean = false,
    val dueDate: Long,
    val priority: Boolean,
)


