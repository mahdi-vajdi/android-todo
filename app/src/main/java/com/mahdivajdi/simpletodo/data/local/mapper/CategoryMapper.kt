package com.mahdivajdi.simpletodo.data.local.mapper

import com.mahdivajdi.simpletodo.data.local.entity.CategoryEntity
import com.mahdivajdi.simpletodo.data.local.mapper.CategoryMapper.toDomain
import com.mahdivajdi.simpletodo.domain.model.Category

object CategoryMapper {

    fun CategoryEntity.toDomain() = Category(
        categoryId = this.categoryId,
        name = this.name,
        description = this.name
    )

    fun Category.toEntity() = CategoryEntity(
        categoryId = this.categoryId,
        name = this.name,
        description = this.name
    )
}