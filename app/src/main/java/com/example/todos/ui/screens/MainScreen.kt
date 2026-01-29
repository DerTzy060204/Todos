package com.example.todos.ui.screens

import androidx.compose.animation.Crossfade // Optional nice transition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todos.data.model.User
import com.example.todos.domain.UserState
import com.example.todos.ui.viewmodel.UserViewModel

/**
 * Main screen acting as the Navigation Host
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userViewModel: UserViewModel = viewModel()
) {
    val userState by userViewModel.userState.collectAsStateWithLifecycle()

    // Local state to track selected user (Simple Navigation)
    // If null -> Show User List. If not null -> Show Todo Screen
    var selectedUser by remember { mutableStateOf<User?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = selectedUser?.let { "${it.name}'s Todos" } ?: "JSONPlaceholder Users")
                },
                navigationIcon = {
                    // Show Back button only if a user is selected
                    if (selectedUser != null) {
                        IconButton(onClick = { selectedUser = null }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->

        // Content Area
        // If a user is selected, show TodoScreen
        if (selectedUser != null) {
            TodoScreen(
                userId = selectedUser!!.id,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        } else {
            // Otherwise show UserList (handled by existing logic)
            when (val state = userState) {
                is UserState.Loading -> {
                    LoadingScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
                is UserState.Success -> {
                    UserListScreen(
                        users = state.users,
                        onUserClick = { user ->
                            selectedUser = user // Navigate to Todo Screen
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
                is UserState.Error -> {
                    ErrorScreen(
                        message = state.message,
                        onRetry = { userViewModel.retry() },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}