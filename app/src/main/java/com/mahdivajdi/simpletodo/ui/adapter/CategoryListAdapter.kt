package com.mahdivajdi.simpletodo.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahdivajdi.simpletodo.databinding.ListItemCategoryBinding
import com.mahdivajdi.simpletodo.domain.model.Category

class CategoryListAdapter(private val onItemClicked: (Long) -> Unit) :
    ListAdapter<Category, CategoryListAdapter.CategoryViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryViewHolder {
        Log.d("categoriesFix", "onCreateViewHolder: category list adapter")
        return CategoryViewHolder(
            ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    class CategoryViewHolder(
        private val binding: ListItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, onItemClicked: (Long) -> Unit) {
            Log.d("categoriesFix", "bind: category list adapter viewholder")
            binding.apply {
                textViewCategoryItemId.text = category.categoryId.toString()
                textViewCategoryItemTitle.text = category.name
                textViewCategoryItemDescription.text = category.description
            }
            itemView.setOnClickListener { onItemClicked(category.categoryId) }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(
                oldItem: Category,
                newItem: Category,
            ): Boolean {
                return (oldItem.categoryId == newItem.categoryId)
            }

            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}