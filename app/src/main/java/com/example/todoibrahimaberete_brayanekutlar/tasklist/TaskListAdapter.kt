package com.example.todoibrahimaberete_brayanekutlar.tasklist

import android.system.Os.bind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoibrahimaberete_brayanekutlar.R


// l'IDE va râler ici car on a pas encore implémenté les méthodes nécessaires
class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<String> = emptyList()

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: String) {
            // on affichera les données ici
          val textView = itemView.findViewById<TextView>(R.id.task_title)
          textView.text = taskTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

       holder.bind(currentList[position])

    }

    override fun getItemCount(): Int {
        return currentList.size;
    }
}
