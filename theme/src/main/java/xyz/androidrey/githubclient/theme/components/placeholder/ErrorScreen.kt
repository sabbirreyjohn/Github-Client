package xyz.androidrey.githubclient.theme.components.placeholder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ErrorScreen(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "failed",
                modifier = Modifier.size(128.dp),  // Sets the size of the icon
                tint = MaterialTheme.colorScheme.error  // Optional: sets the icon color
            )
            Text(text = message)
        }
    }
}
@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(message = "Something went wrong!")
}