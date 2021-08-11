package com.hk.todolist

data class Todo(
    val title: String,
    var isChecked: Boolean = false
)