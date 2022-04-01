package com.example.todoibrahimaberete_brayanekutlar.tasklist

import android.system.Os.bind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoibrahimaberete_brayanekutlar.R
object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.id == newItem.id
    }
        // are they the same "entity" ? (usually same id)
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    // do they have the same data ? (content)
}

// l'IDE va râler ici car on a pas encore implémenté les méthodes nécessaires
class TaskListAdapter : ListAdapter<Task,TaskListAdapter.TaskViewHolder>(TaskDiffCallback) {

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            // on affichera les données ici
          val titleView = itemView.findViewById<TextView>(R.id.task_title)
          titleView.text = task.title
            val descriptionView = itemView.findViewById<TextView>(R.id.task_description)
            descriptionView.text = task.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
       holder.bind(currentList[position])
    }
}
