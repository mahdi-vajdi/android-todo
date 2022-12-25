package com.mahdivajdi.simpletodo.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


private const val TABLE_NAME = "task_table"

@Entity(tableName = TABLE_NAME)
data class TaskLocalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val timestamp: Long,
    val done: Boolean,
    val tags: List<String>,
)


@Dao
interface TaskDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getTasks(): Flow<List<TaskLocalModel>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getTask(id: Int): LiveData<TaskLocalModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(TaskLocalModel: TaskLocalModel): Long

    @Update
    suspend fun updateTask(TaskLocalModel: TaskLocalModel)

    @Delete
    suspend fun deleteTask(TaskLocalModel: TaskLocalModel)
    
}

fun TaskLocalModel.toDomainModel() = TaskLocalModel(
    id = this.id,
    title = this.title,
    description = this.description,
    timestamp = this.timestamp,
    done = this.done,
    tags = this.tags,
)

