package com.example.todos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.data.model.Todo
import com.example.todos.data.repository.UserRepository
import com.example.todos.domain.TodoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TodoViewModel : ViewModel() {
    private val repository = UserRepository()
    private val _todoState = MutableStateFlow<TodoState>(TodoState.Loading)
    val todoState: StateFlow<TodoState> = _todoState.asStateFlow()

    private var currentUserId: Int = -1

    /**
     * Loads todos for a specific user.
     * Checks if data is already loaded for this user to avoid unnecessary calls.
     */
    fun loadTodos(userId: Int) {
        if (currentUserId == userId && _todoState.value is TodoState.Success) return

        currentUserId = userId
        fetchFromApi(userId)
    }

    fun retry() {
        if (currentUserId != -1) {
            fetchFromApi(currentUserId)
        }
    }

    private fun fetchFromApi(userId: Int) {
        viewModelScope.launch {
            _todoState.value = TodoState.Loading

            repository.getTodos(userId)
                .onSuccess { todos ->
                    _todoState.value = TodoState.Success(todos)
                }
                .onFailure { exception ->
                    val errorMessage = when (exception) {
                        is UnknownHostException -> "No internet connection."
                        is SocketTimeoutException -> "Connection timeout."
                        else -> exception.message ?: "Unknown error"
                    }
                    _todoState.value = TodoState.Error(errorMessage)
                }
        }
    }

    /**
     * Toggles the completion status of a todo.
     * Updates the local state immediately for UI responsiveness.
     */
    fun toggleTodo(todoId: Int) {
        val currentState = _todoState.value
        if (currentState is TodoState.Success) {
            val updatedList = currentState.todos.map { todo ->
                if (todo.id == todoId) {
                    todo.copy(completed = !todo.completed)
                } else {
                    todo
                }
            }
            _todoState.value = TodoState.Success(updatedList)
        }
    }
}