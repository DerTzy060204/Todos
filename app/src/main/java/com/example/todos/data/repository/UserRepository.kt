package com.example.todos.data.repository

import com.example.todos.data.model.User
import com.example.todos.data.remote.RetrofitClient
import kotlinx.coroutines. Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class to handle data operations
 * Acts as a single source of truth for user data
 * Separates the data layer from the UI layer
 */
class UserRepository {

    // API service instance from RetrofitClient
    private val apiService = RetrofitClient.apiService

    /**
     * Fetches users from the API
     * Uses withContext(Dispatchers.IO) to ensure network call happens on IO thread
     *
     * @return Result<List<User>> - Success with user list or Failure with exception
     */
    suspend fun getUsers(): Result<List<User>> {
        return withContext(Dispatchers.IO) {
            try {
                // Make the API call
                val response = apiService.getUsers()

                // Check if response is successful (HTTP 200-299)
                if (response. isSuccessful) {
                    // Get the body of response (list of users)
                    val users = response.body()

                    // If body is not null, return success
                    if (users != null) {
                        Result.success(users)
                    } else {
                        // Body is null, return failure
                        Result.failure(Exception("Empty response body"))
                    }
                } else {
                    // HTTP error (4xx, 5xx)
                    Result.failure(Exception("HTTP Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e:  Exception) {
                // Network error, timeout, or parsing error
                Result.failure(e)
            }
        }
    }
}