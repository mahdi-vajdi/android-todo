package com.mahdivajdi.simpletodo.data.local.mapper

import com.mahdivajdi.simpletodo.data.local.entity.CategoryEntity
import com.mahdivajdi.simpletodo.domain.model.Category

object CategoryMapper {

    fun CategoryEntity.toDomain() = Category(
        categoryId = this.categoryId,
        title = this.title,
    )

    fun Category.toEntity() = CategoryEntity(
        categoryId = this.categoryId,
        title = this.title,
    )
}