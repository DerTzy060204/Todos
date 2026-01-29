package com.example.todos.data.remote

import com.example.todos.data.model.Todo // Import Todo
import com.example.todos.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query // Import Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    /**
     * Fetches todos for a specific user
     * JSONPlaceholder supports query params: /todos?userId=1
     */
    @GET("todos")
    suspend fun getTodos(@Query("userId") userId: Int): Response<List<Todo>>
}