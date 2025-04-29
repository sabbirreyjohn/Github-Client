package xyz.androidrey.githubclient.theme.components.placeholder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.androidrey.githubclient.theme.AppTheme
import xyz.androidrey.githubclient.theme.components.ThePreview

@Composable
fun FullScreenCircularProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@ThePreview
@Composable
private fun FullScreenCircularProgressIndicatorPreview() {
    AppTheme {
        Surface {
            FullScreenCircularProgressIndicator()
        }
    }
}
