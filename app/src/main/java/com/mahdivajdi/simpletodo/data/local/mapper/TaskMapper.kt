package com.mahdivajdi.simpletodo.data.local.mapper

import com.mahdivajdi.simpletodo.data.local.entity.TaskEntity
import com.mahdivajdi.simpletodo.domain.model.Task

object TaskMapper {

    fun Task.toEntity() = TaskEntity(
        taskId = this.taskId,
        taskCategoryId = this.taskCategoryId,
        title = this.title,
        description = this.description,
        timestamp = this.timestamp,
        done = this.done,
    )

    fun TaskEntity.toDomain() = Task(
        taskId = this.taskId,
        taskCategoryId = this.taskCategoryId,
        title = this.title,
        description = this.description,
        timestamp = this.timestamp,
        done = this.done,
    )
}