package com.hk.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TaskDao {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(task: TasksEntity)

        @Delete
        suspend fun delete(task: TasksEntity)

        @Query("DELETE FROM task_table")
        suspend fun ClearTable()

        @Query("SELECT * FROM task_table ORDER BY id ASC")
        fun getAllTasks(): LiveData<List<TasksEntity>>


}