package com.mahdivajdi.simpletodo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahdivajdi.simpletodo.databinding.ListItemTaskBinding
import com.mahdivajdi.simpletodo.domain.model.Task
import java.time.LocalDate

class TaskListAdapter(private val onItemClicked: (Long) -> Unit) :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback) {


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


        fun bind(task: Task, onItemClicked: (Long) -> Unit) {
            binding.apply {
                checkBoxTaskItemState.isChecked = task.state
                textViewTaskItemTitle.text = task.title
                textViewTaskItemDueDate.text = task.dueDate.let {
                    if (it == 0L) ""
                    else LocalDate.ofEpochDay(it).toString()
                }
                checkBoxTaskItemPriority.isChecked = task.priority
            }
            itemView.setOnClickListener { onItemClicked(task.taskId) }
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(
                oldItem: Task,
                newItem: Task,
            ): Boolean {
                return (oldItem.taskId == newItem.taskId)
            }

            override fun areContentsTheSame(
                oldItem: Task,
                newItem: Task,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}