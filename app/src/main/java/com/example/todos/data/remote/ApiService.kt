package com.example.todos.data.remote

import com.example.todos.data.model.User
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit API interface for JSONPlaceholder endpoints
 */
interface ApiService {

    /**
     * Fetches all users from the /users endpoint
     * @return Response containing a list of User objects
     */
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}