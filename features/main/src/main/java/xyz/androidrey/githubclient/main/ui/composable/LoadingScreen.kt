package xyz.androidrey.githubclient.main.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.androidrey.githubclient.theme.components.placeholder.ShimmerRepoCardPlaceholder
import xyz.androidrey.githubclient.theme.components.placeholder.ShimmerUserCardPlaceholder

@Composable
fun UserLoadingScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(10) {
            ShimmerUserCardPlaceholder()
        }
    }
}

@Composable
fun RepositoryLoadingScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            ShimmerRepoCardPlaceholder()
        }
    }
}