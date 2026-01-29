package com.example.todos.domain

import com.example.todos.data.model.Todo

sealed class TodoState {
    data object Loading : TodoState()
    data class Success(val todos: List<Todo>) : TodoState()
    data class Error(val message: String) : TodoState()
}