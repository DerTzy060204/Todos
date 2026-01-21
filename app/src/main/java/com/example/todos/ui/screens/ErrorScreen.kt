package com.example.todos.ui.screens

import androidx.compose. foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation. layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose. foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx. compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Separate composable for Error screen
 * Displays error message with retry button
 *
 * @param message Error message to display
 * @param onRetry Callback function when retry button is clicked
 */
@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Box centers content in the middle of the screen
    Box(
        modifier = modifier. fillMaxSize(), // Fill entire screen
        contentAlignment = Alignment. Center // Center content
    ) {
        Column(
            modifier = Modifier.padding(16.dp), // Padding around content
            horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
        ) {
            // Error icon (warning symbol)
            Icon(
                imageVector = Icons.Default. Warning, // Material warning icon
                contentDescription = "Error",
                modifier = Modifier.size(64.dp), // Size of icon
                tint = MaterialTheme.colorScheme.error // Use error color from theme
            )

            // Space between icon and text
            Spacer(modifier = Modifier.height(16.dp))

            // Error title
            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme. typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            // Space between title and message
            Spacer(modifier = Modifier.height(8.dp))

            // Error message
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f), // Slightly transparent
                textAlign = TextAlign.Center
            )

            // Space between message and button
            Spacer(modifier = Modifier.height(24.dp))

            // Retry button
            Button(
                onClick = onRetry // Call the retry callback when clicked
            ) {
                Text(text = "Retry")
            }
        }
    }
}

/**
 * Preview for ErrorScreen in Android Studio
 */
@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        message = "No internet connection. Please check your network settings.",
        onRetry = {}
    )
}