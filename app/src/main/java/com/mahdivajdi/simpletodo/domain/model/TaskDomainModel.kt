package com.mahdivajdi.simpletodo.domain.model

import com.mahdivajdi.simpletodo.data.local.TaskLocalModel

data class TaskDomainModel(
    val id: Int = 0,
    var title: String,
    var description: String,
    val timestamp: Long,
    var done: Boolean = false,
    var tag: String?,
)

fun TaskDomainModel.toLocalModel() = TaskLocalModel(
    id = this.id,
    title = this.title,
    description = this.description,
    timestamp = this.timestamp,
    done = this.done,
    tag = this.tag,
)
