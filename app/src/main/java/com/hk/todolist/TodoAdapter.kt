package com.hk.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hk.todolist.database.TasksEntity

class TodoAdapter(private  val context: Context, private  val listener: ITodoAdapter): RecyclerView.Adapter<TodoAdapter.TodoViewHOlder>(){
    private val allTasks= mutableListOf<TasksEntity>()

    inner class TodoViewHOlder(itemView: View):  RecyclerView.ViewHolder(itemView){
        val textView=itemView.findViewById<TextView>(R.id.tvTodoTask)
        val deleteButton=itemView.findViewById<ImageView>(R.id.btnDeleteDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHOlder {
       val viewHolder = TodoViewHOlder(LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false))
       viewHolder.deleteButton.setOnClickListener {
           listener.onItemClick(allTasks[viewHolder.adapterPosition])
       }
        return viewHolder
    }

    override fun onBindViewHolder(holder: TodoViewHOlder, position: Int) {
        val currentTask=allTasks[position]
        holder.apply {
            textView.text=currentTask.task
        }

    }

    override fun getItemCount(): Int {
        return allTasks.size
    }

    fun updateList(newTasks: List<TasksEntity>)
    {
        allTasks.clear()
        allTasks.addAll(newTasks)

        notifyDataSetChanged()
    }
}

interface ITodoAdapter{
    fun onItemClick(task: TasksEntity)
}















