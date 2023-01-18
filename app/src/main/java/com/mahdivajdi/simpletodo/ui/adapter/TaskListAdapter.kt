package com.mahdivajdi.simpletodo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahdivajdi.simpletodo.databinding.ListItemTaskBinding
import com.mahdivajdi.simpletodo.domain.model.Task
import com.mahdivajdi.simpletodo.ui.dueDateString

class TaskListAdapter(
    private val onItemClicked: (Long) -> Unit,
    private val toggleTaskState: (Long) -> Unit,
    private val toggleTaskPriority: (Long) -> Unit,
) :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ListItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked, toggleTaskState, toggleTaskPriority)
    }

    class TaskViewHolder(val binding: ListItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            task: Task,
            onItemClicked: (Long) -> Unit,
            toggleTaskState: (Long) -> Unit,
            toggleTaskPriority: (Long) -> Unit,
        ) {
            binding.apply {
                // Setup binding variables
                this.task = task
                this.executePendingBindings()

                val dueDate = dueDateString(task.dueDate)
//                textViewTaskItemDueDate.visibility = if (dueDate.isNullOrBlank()) GONE else VISIBLE
                textViewTaskItemDueDate.text = dueDate
                // State checkbox
                checkBoxTaskItemState.setOnClickListener {
                    toggleTaskState(task.taskId)
                }

                // Priority Checkbox
                checkBoxTaskItemPriority.setOnClickListener {
                    toggleTaskPriority(task.taskId)
                }

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