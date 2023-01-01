package com.mahdivajdi.simpletodo.domain.model

data class Category(
    val categoryId: Long = 0,
    val name: String,
    val description: String
) {

    override fun toString(): String {
        return name
    }
}
