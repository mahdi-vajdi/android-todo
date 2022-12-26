package com.mahdivajdi.simpletodo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahdivajdi.simpletodo.databinding.ListItemTaskBinding
import com.mahdivajdi.simpletodo.domain.model.TaskDomainModel

class TaskListAdapter : ListAdapter<TaskDomainModel, TaskListAdapter.TaskViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ListItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskViewHolder(private val binding: ListItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskDomainModel) {
            binding.apply {
                textViewTasksId.text = task.id.toString()
                textViewTasksTitle.text = task.title
                textViewTasksTimestamp.text = task.timestamp.toString()
                textViewTasksDescription.text = task.description
                textViewTasksDone.text = task.done.toString()
                textViewTasksTags.text = task.tag.toString()
            }
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