package com.example.todoibrahimaberete_brayanekutlar.tasklist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// l'IDE va râler ici car on a pas encore implémenté les méthodes nécessaires
class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<String>

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: String) {
            // on affichera les données ici
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
