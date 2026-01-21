package com.example.todos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout. PaddingValues
import androidx. compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose. foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose. foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx. compose.material3.MaterialTheme
import androidx.compose. material3.Text
import androidx. compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui. text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todos.data.model.Address
import com.example.todos.data.model. Company
import com.example.todos.data.model. Geo
import com.example.todos.data.model. User

/**
 * Main screen displaying the list of users
 * Uses LazyColumn for performance - only renders visible items
 *
 * @param users List of users to display
 */
@Composable
fun UserListScreen(
    users: List<User>,
    modifier: Modifier = Modifier
) {
    // LazyColumn is like RecyclerView - only composes visible items
    // Perfect for large lists for better performance
    LazyColumn(
        modifier = modifier.fillMaxSize(), // Fill entire screen
        contentPadding = PaddingValues(16.dp), // Padding around the list
        verticalArrangement = Arrangement.spacedBy(12.dp) // Space between items
    ) {
        // items() is a LazyColumn DSL that creates items from a list
        // key = { it.id } helps with recomposition optimization
        items(
            items = users,
            key = { user -> user.id } // Unique key for each item
        ) { user ->
            // Composable for each user item
            UserItem(user = user)
        }
    }
}

/**
 * Composable for individual user card
 * Displays user information in a Material Design card
 *
 * @param user User object to display
 */
@Composable
fun UserItem(
    user: User,
    modifier: Modifier = Modifier
) {
    // Card provides elevation and shape
    Card(
        modifier = modifier. fillMaxWidth(), // Card fills width of parent
        elevation = CardDefaults. cardElevation(
            defaultElevation = 4.dp // Elevation (shadow) of card
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme. colorScheme.surface // Card background color
        )
    ) {
        // Column arranges children vertically
        Column(
            modifier = Modifier. padding(16.dp) // Padding inside card
        ) {
            // User name - bold and larger text
            Text(
                text = user.name,
                style = MaterialTheme. typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme. onSurface
            )

            // Username - smaller text
            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography. bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) // Slightly transparent
            )

            // Email with icon-like prefix
            Text(
                text = "📧 ${user.email}",
                style = MaterialTheme. typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 8.dp)
            )

            // Phone with icon-like prefix
            Text(
                text = "📞 ${user.phone}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )

            // City with icon-like prefix
            Text(
                text = "📍 ${user.address.city}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )

            // Company name with icon-like prefix
            Text(
                text = "🏢 ${user.company.name}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme. colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

/**
 * Preview for UserItem in Android Studio
 */
@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    val sampleUser = User(
        id = 1,
        name = "Leanne Graham",
        username = "Bret",
        email = "leanne@example.com",
        phone = "1-770-736-8031",
        website = "hildegard.org",
        address = Address(
            street = "Kulas Light",
            suite = "Apt. 556",
            city = "Gwenborough",
            zipcode = "92998-3874",
            geo = Geo(lat = "-37.3159", lng = "81.1496")
        ),
        company = Company(
            name = "Romaguera-Crona",
            catchPhrase = "Multi-layered client-server neural-net",
            bs = "harness real-time e-markets"
        )
    )

    UserItem(user = sampleUser)
}