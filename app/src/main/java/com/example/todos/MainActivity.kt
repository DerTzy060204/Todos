package com.example.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.todos.ui.screens.MainScreen

/**
 * Main Activity - Entry point of the app
 * Uses Jetpack Compose for UI instead of XML layouts
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display (draws behind system bars)
        enableEdgeToEdge()

        // setContent is the Compose way of setting the UI
        // Replaces setContentView(R.layout.activity_main)
        setContent {
            // MaterialTheme provides Material Design 3 theming
            MaterialTheme {
                // Surface is a container with background color
                Surface {
                    // Display the main screen
                    MainScreen()
                }
            }
        }
    }
}