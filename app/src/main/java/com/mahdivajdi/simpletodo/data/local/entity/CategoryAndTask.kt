package com.mahdivajdi.simpletodo.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryAndTask(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "task_category_id"
    )
    val tasks: List<TaskEntity>,
)
