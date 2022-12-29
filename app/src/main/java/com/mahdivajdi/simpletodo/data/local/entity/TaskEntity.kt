package com.mahdivajdi.simpletodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

const val TASK_TABLE_NAME = "task"

@Entity(tableName = TASK_TABLE_NAME)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "task_id") val taskId: Long = 0,
    @ColumnInfo(name = "task_category_id") val taskCategoryId: Long,
    val title: String,
    val description: String,
    val timestamp: Long,
    val done: Boolean,
)
