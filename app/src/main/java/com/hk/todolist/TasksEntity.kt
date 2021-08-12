package com.hk.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class TasksEntity(@ColumnInfo(name = "task") val task: String,
                       @ColumnInfo(name = "isChecked") var isChecked: Boolean) {
    @PrimaryKey(autoGenerate = true) var id =0
}