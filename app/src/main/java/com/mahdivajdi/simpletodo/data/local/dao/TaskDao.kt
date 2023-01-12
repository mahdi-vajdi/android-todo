package com.mahdivajdi.simpletodo.data.local.dao

import androidx.room.*
import com.mahdivajdi.simpletodo.data.local.entity.TASK_TABLE_NAME
import com.mahdivajdi.simpletodo.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM $TASK_TABLE_NAME")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE task_id = :id")
    fun getTask(id: Long): Flow<TaskEntity>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE priority = 1")
    fun getPriorityTasks() : Flow<List<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE_NAME WHERE dueDate = :date")
    fun getTasksWithDate(date: Long): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity): Long

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Query("UPDATE $TASK_TABLE_NAME SET state = NOT state WHERE task_id = :taskId")
    suspend fun toggleTaskState(taskId: Long)

    @Query("UPDATE $TASK_TABLE_NAME SET priority = NOT priority WHERE task_id = :taskId")
    suspend fun toggleTaskPriority(taskId: Long)

    @Query("DELETE FROM $TASK_TABLE_NAME WHERE task_id = :taskId")
    suspend fun deleteTask(taskId: Long)
    
}

