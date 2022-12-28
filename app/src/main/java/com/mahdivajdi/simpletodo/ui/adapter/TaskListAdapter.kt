package com.mahdivajdi.simpletodo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahdivajdi.simpletodo.databinding.ListItemTaskBinding
import com.mahdivajdi.simpletodo.domain.model.TaskDomainModel

class TaskListAdapter(private val onItemClicked: (Int) -> Unit) :
    ListAdapter<TaskDomainModel, TaskListAdapter.TaskViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ListItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    class TaskViewHolder(
        private val binding: ListItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(task: TaskDomainModel, onItemClicked: (Int) -> Unit) {
            binding.apply {
                textViewTaskItemId.text = task.id.toString()
                textViewTaskItemTitle.text = task.title
                textViewTaskItemTimestamp.text = task.timestamp.toString()
                textViewTaskItemDescription.text = task.description
                textViewTaskItemDone.text = task.done.toString()
                textViewTaskItemTags.text = task.tag.toString()
            }
            itemView.setOnClickListener { onItemClicked(task.id) }
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TaskDomainModel>() {
            override fun areItemsTheSame(
                oldItem: TaskDomainModel,
                newItem: TaskDomainModel,
            ): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(
                oldItem: TaskDomainModel,
                newItem: TaskDomainModel,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}