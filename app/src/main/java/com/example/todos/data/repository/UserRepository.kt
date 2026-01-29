package com.example.todos.data.repository

import com.example.todos.data.model.Todo
import com.example.todos.data.model.User
import com.example.todos.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getUsers(): Result<List<User>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getUsers()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("HTTP Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Fetches todos for a specific user ID
     */
    suspend fun getTodos(userId: Int): Result<List<Todo>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTodos(userId)
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("HTTP Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}