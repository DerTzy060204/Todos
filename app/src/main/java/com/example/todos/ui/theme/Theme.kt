package com.example.todos.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Define the custom color scheme
// We use the same colors for both Light and Dark modes to enforce your specific look
private val CustomColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = White, // Text color on Top Bar

    background = BlackBackground,
    onBackground = White, // Text color on Background

    surface = BlackBackground,
    onSurface = White,

    // For Todo Items (Surface Variant)
    surfaceVariant = DarkSurface,
    onSurfaceVariant = White,

    secondary = Orange,
    tertiary = Orange
)

@Composable
fun TodosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set dynamicColor to false to force our Orange/Black theme over the user's wallpaper colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Always use our Custom Scheme to enforce Black/Orange
        else -> CustomColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}