package com.mahdivajdi.simpletodo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CATEGORY_TABLE_NAME = "category"

@Entity(tableName = CATEGORY_TABLE_NAME)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id") val categoryId: Long = 0,
    val name: String,
    val description: String
)