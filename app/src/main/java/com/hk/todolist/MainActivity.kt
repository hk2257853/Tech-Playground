package com.hk.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hk.todolist.database.TaskViewModel
import com.hk.todolist.database.TasksEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ITodoAdapter {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var viewModel: TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvTodotasks.layoutManager = LinearLayoutManager(this)
        val todoAdapter = TodoAdapter(this, this)
        rvTodotasks.adapter = todoAdapter

        viewModel=ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TaskViewModel::class.java)
        viewModel.allTasks.observe(this, Observer {
            list->list?.let {
            todoAdapter.updateList(it)
        }
        })

        btnAddTodo.setOnClickListener {
            val todoTask = etTodoTitle.text.toString()
            if(todoTask.isNotEmpty()) {
                viewModel.insertNote(TasksEntity(todoTask))
                etTodoTitle.text.clear()
            }
        }

        btnDeleteAll.setOnClickListener {
            viewModel.deleteTable()
        }
    }

    override fun onItemClick(task: TasksEntity) {
        viewModel.delete(task)
    }
}
