package com.mahdivajdi.simpletodo.data.repository

import com.mahdivajdi.simpletodo.data.local.dao.CategoryDao
import com.mahdivajdi.simpletodo.data.local.mapper.CategoryMapper.toDomain
import com.mahdivajdi.simpletodo.data.local.mapper.CategoryMapper.toEntity
import com.mahdivajdi.simpletodo.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepository(private val dataSource: CategoryDao) {

    fun getCategories(): Flow<List<Category>> = dataSource.getCategories().map { list ->
        list.map {
            it.toDomain()
        }
    }

    fun getCategory(categoryId: Long): Flow<Category> = dataSource.getCategory(categoryId).map {
        it.toDomain()
    }



    suspend fun insertCategory(category: Category) =
        dataSource.insertCategory(category.toEntity())

    suspend fun updateCategory(category: Category) =
        dataSource.updateCategory(category.toEntity())

    suspend fun deleteCategory(categoryId: Long) = dataSource.deleteCategory(categoryId)


}