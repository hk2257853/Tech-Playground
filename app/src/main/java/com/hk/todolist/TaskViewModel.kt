package com.hk.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {

    val allTasks: LiveData<List<TasksEntity>>
    val repository:  TaskRepository

    init {
        val dao=TaskDatabase.getDatabase(application).getTaskDao()
        repository=TaskRepository(dao)
        allTasks=repository.allTasks
    }

    fun insertNote(task: TasksEntity)=viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }


    fun delete(task: TasksEntity)=viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }


}