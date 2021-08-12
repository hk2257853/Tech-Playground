package com.hk.todolist.database

import androidx.lifecycle.LiveData


class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<TasksEntity>> = taskDao.getAllTasks()

    suspend fun insert(task: TasksEntity){
        taskDao.insert(task)
    }

    suspend fun delete(task: TasksEntity){
    taskDao.delete(task)
    }

    suspend fun deleteTable(){
        taskDao.ClearTable()
    }
}