package com.mahdivajdi.simpletodo.domain.model

data class Category(
    val categoryId: Long = 0,
    val title: String,
) {

    override fun toString(): String {
        return title
    }
}
