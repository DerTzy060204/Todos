package com.example.todos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todos.data.model.Todo
import com.example.todos.domain.TodoState
import com.example.todos.ui.viewmodel.TodoViewModel

@Composable
fun TodoScreen(
    userId: Int,
    viewModel: TodoViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // Trigger data fetch when the screen is composed with a new userId
    LaunchedEffect(userId) {
        viewModel.loadTodos(userId)
    }

    val todoState by viewModel.todoState.collectAsStateWithLifecycle()

    when (val state = todoState) {
        is TodoState.Loading -> LoadingScreen(modifier)
        is TodoState.Error -> ErrorScreen(
            message = state.message,
            onRetry = { viewModel.retry() },
            modifier = modifier
        )
        is TodoState.Success -> {
            TodoList(
                todos = state.todos,
                onToggle = { viewModel.toggleTodo(it) },
                modifier = modifier
            )
        }
    }
}

@Composable
fun TodoList(
    todos: List<Todo>,
    onToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = todos,
            key = { it.id }
        ) { todo ->
            TodoItem(todo = todo, onToggle = onToggle)
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onToggle: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(todo.id) } // Allow clicking the whole card to toggle
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.completed,
                onCheckedChange = { onToggle(todo.id) }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                // Add strikethrough if completed
                textDecoration = if (todo.completed) TextDecoration.LineThrough else null,
                color = if (todo.completed)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                else
                    MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}