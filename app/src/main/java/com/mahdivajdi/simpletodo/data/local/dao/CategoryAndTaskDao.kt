package com.mahdivajdi.simpletodo.data.local.dao

import androidx.room.Query
import androidx.room.Transaction
import com.mahdivajdi.simpletodo.data.local.entity.CategoryAndTask

interface CategoryAndTaskDao {

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoryTasks() : List<CategoryAndTask>

}