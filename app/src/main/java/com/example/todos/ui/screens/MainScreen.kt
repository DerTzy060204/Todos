package com.example.todos.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose. foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose. runtime.Composable
import androidx. compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose. viewModel
import com.example.todos.domain.UserState
import com.example.todos.ui.viewmodel.UserViewModel

/**
 * Main screen that orchestrates the UI based on state
 * Observes the StateFlow from ViewModel and displays appropriate screen
 */
@OptIn(ExperimentalMaterial3Api::class) // TopAppBar requires opt-in
@Composable
fun MainScreen(
    viewModel: UserViewModel = viewModel() // Get or create ViewModel
) {
    // Collect state from ViewModel's StateFlow
    // collectAsStateWithLifecycle is lifecycle-aware - stops collecting when inactive
    val userState by viewModel.userState.collectAsStateWithLifecycle()

    // Scaffold provides basic Material Design layout structure
    // Includes TopBar, BottomBar, FAB slots
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // Top app bar with title
            TopAppBar(
                title = {
                    Text(text = "JSONPlaceholder Users")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme. primary,
                    titleContentColor = MaterialTheme.colorScheme. onPrimary
                )
            )
        }
    ) { paddingValues ->
        // When statement handles different states
        // Smart casting automatically casts to specific state type
        when (val state = userState) {
            // Loading state - show loading screen
            is UserState.Loading -> {
                LoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Apply scaffold padding
                )
            }

            // Success state - show user list
            is UserState.Success -> {
                UserListScreen(
                    users = state.users, // Get users from Success state
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Apply scaffold padding
                )
            }

            // Error state - show error screen
            is UserState.Error -> {
                ErrorScreen(
                    message = state.message, // Get error message from Error state
                    onRetry = { viewModel.retry() }, // Call retry on ViewModel
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Apply scaffold padding
                )
            }
        }
    }
}