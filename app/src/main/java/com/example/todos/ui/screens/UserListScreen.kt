package com.example.todos.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todos.data.model.User

@Composable
fun UserListScreen(
    users: List<User>,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = users,
            key = { user -> user.id }
        ) { user ->
            UserItem(
                user = user,
                onClick = { onUserClick(user) }
            )
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Attractive Dark Gray Card Design
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        // USE GRAY COLOR HERE
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C2C2C) // Sleek Dark Gray
        ),
        // Add a subtle border for better separation from black background
        border = BorderStroke(1.dp, Color(0xFF444444)),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(20.dp) // slightly more padding for breathing room
        ) {
            // 1. Name (White for high contrast)
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // 2. Username (Orange/Primary color for style)
            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary, // Matches your TopBar
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // 3. Email Row
            UserDetailItem(
                icon = Icons.Outlined.Email,
                text = user.email,
                iconTint = Color(0xFF90CAF9) // Light Blue (visible on dark)
            )

            // 4. Phone Row
            UserDetailItem(
                icon = Icons.Outlined.Phone,
                text = user.phone,
                iconTint = Color(0xFFB0BEC5) // Light Gray Icon
            )

            // 5. Address Row
            UserDetailItem(
                icon = Icons.Filled.LocationOn,
                text = user.address.city,
                iconTint = Color(0xFFEF9A9A) // Light Red (visible on dark)
            )

            // 6. Company Row
            UserDetailItem(
                icon = Icons.Filled.Apartment,
                text = user.company.name,
                iconTint = Color(0xFFEEEEEE) // White/Gray Icon
            )
        }
    }
}

@Composable
fun UserDetailItem(
    icon: ImageVector,
    text: String,
    iconTint: Color
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,  
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Text is Light Gray to be readable on the Gray card
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFE0E0E0) // Light Gray Text
        )
    }
}