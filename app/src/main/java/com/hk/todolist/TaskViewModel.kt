package com.hk.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TaskViewModel(application: Application): AndroidViewModel(application) {
    val allTasks: LiveData<List<TasksEntity>>

    init {
        val dao=TaskDatabase.getDatabase(application).getTaskDao()
        val repository=TaskRepository(dao)
        allTasks=repository.allTasks
    }

}