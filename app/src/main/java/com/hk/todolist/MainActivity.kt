package com.hk.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var viewModel: TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf())

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTask = etTodoTitle.text.toString()
            if(todoTask.isNotEmpty()) {
                viewModel.insertNote(TasksEntity(todoTask, true))
                etTodoTitle.text.clear()
            }
        }
        btnDeleteDoneTodos.setOnClickListener {
           //viewModel.delete(TasksEntity(task), TasksEntity(isChecked))
        }

        /*btnDeleteAll.setOnClickListener{
        }*/



        viewModel=ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TaskViewModel::class.java)
        viewModel.allTasks.observe(this, Observer {
            list->list?.let {
            todoAdapter.updateList(it)
        }
        })

    }
}

//TODO:
/*
* update each time there's a change in tick/check.
*
* delete a particular row from db(means delete all those rows for which isChecked is true).
* functionality to delete all data from database
*
* */