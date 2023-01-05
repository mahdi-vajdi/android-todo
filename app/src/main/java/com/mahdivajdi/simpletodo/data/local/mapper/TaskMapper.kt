package com.mahdivajdi.simpletodo.data.local.mapper

import com.mahdivajdi.simpletodo.data.local.entity.TaskEntity
import com.mahdivajdi.simpletodo.data.local.mapper.TaskMapper.toEntity
import com.mahdivajdi.simpletodo.domain.model.Task

object TaskMapper {

    fun Task.toEntity() = TaskEntity(
        taskId = this.taskId,
        taskCategoryId = this.taskCategoryId,
        title = this.title,
        detail = this.detail,
        dateModified = this.dateModified,
        state = this.state,
        schedule = this.schedule,
        priority = this.priority
    )

    fun TaskEntity.toDomain() = Task(
        taskId = this.taskId,
        taskCategoryId = this.taskCategoryId,
        title = this.title,
        detail = this.detail,
        dateModified = this.dateModified,
        state = this.state,
        schedule = this.schedule,
        priority = this.priority
    )
}