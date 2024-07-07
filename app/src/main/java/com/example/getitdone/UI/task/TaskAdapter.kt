package com.example.getitdone.UI.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getitdone.DATA.Task
import com.example.getitdone.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TaskAdapter(private val tasks: List<Task>, private val listener: TaskUpdatedListener) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.textViewTitle.text = task.title
            binding.textViewDetails.text = task.description
            binding.checkBox.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> {
                        task.copy(isComplete = true)
                    }

                    else -> {
                        task.copy(isComplete = false)
                    }
                }
                listener.onTaskUpdated(updatedTask)
            }
            binding.checkBoxSTAR.addOnCheckedStateChangedListener { _, state ->
                val starredTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> {
                        task.copy(isStarred = true)
                    }

                    else -> {
                        task.copy(isStarred = false)
                    }

                }
                listener.onTaskUpdated(starredTask)

            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }
}




