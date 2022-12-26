package com.mahdivajdi.simpletodo.data.local

import androidx.room.*
import com.mahdivajdi.simpletodo.domain.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow


private const val TABLE_NAME = "task_table"

@Entity(tableName = TABLE_NAME)
data class TaskLocalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val timestamp: Long,
    val done: Boolean,
    val tag: String?,
)


@Dao
interface TaskDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getTasks(): Flow<List<TaskLocalModel>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getTask(id: Int): Flow<TaskLocalModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskLocalModel: TaskLocalModel): Long

    @Update
    suspend fun updateTask(taskLocalModel: TaskLocalModel)

    @Delete
    suspend fun deleteTask(taskLocalModel: TaskLocalModel)
    
}

fun TaskLocalModel.toDomainModel() = TaskDomainModel(
    id = this.id,
    title = this.title,
    description = this.description,
    timestamp = this.timestamp,
    done = this.done,
    tag = this.tag,
)

