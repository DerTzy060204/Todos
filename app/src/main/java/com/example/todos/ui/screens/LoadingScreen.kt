package com.example.todos.ui. screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation. layout.Column
import androidx.compose.foundation. layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose. foundation.layout.height
import androidx.compose.foundation. layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose. material3.Text
import androidx. compose.runtime.Composable
import androidx.compose.ui. Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Separate composable for Loading screen
 * Displays a circular progress indicator with text
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    // Box centers its content in the middle of the screen
    Box(
        modifier = modifier. fillMaxSize(), // Fill entire screen
        contentAlignment = Alignment.Center // Center content
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
        ) {
            // Circular progress indicator (spinning loader)
            CircularProgressIndicator(
                modifier = Modifier. size(64.dp), // Size of the progress indicator
                color = MaterialTheme.colorScheme.primary // Use theme color
            )

            // Space between loader and text
            Spacer(modifier = Modifier.height(16.dp))

            // Loading text
            Text(
                text = "Loading users...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

/**
 * Preview for LoadingScreen in Android Studio
 */
@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}