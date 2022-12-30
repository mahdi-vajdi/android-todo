package com.mahdivajdi.simpletodo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mahdivajdi.simpletodo.data.local.entity.CATEGORY_TABLE_NAME
import com.mahdivajdi.simpletodo.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM $CATEGORY_TABLE_NAME WHERE category_id = :categoryId")
    fun getCategory(categoryId: Long): Flow<CategoryEntity>

    @Insert
    suspend fun insertCategory(category: CategoryEntity): Long

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Query("DELETE FROM $CATEGORY_TABLE_NAME WHERE category_id = :categoryId")
    suspend fun deleteCategory(categoryId: Long)

}