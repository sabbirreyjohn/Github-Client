package xyz.androidrey.githubclient.common.ui.state

import androidx.compose.runtime.Composable
import xyz.androidrey.githubclient.theme.components.placeholder.ErrorScreen
import xyz.androidrey.githubclient.theme.components.placeholder.FullScreenCircularProgressIndicator


@Composable
fun <T> UiStateHandler(
    state: UiState<T>,
    loading: @Composable () -> Unit = { FullScreenCircularProgressIndicator() },
    error: @Composable (String) -> Unit = { ErrorScreen(it) },
    success: @Composable (T) -> Unit
) {
    when (state) {
        is UiState.Loading -> loading()
        is UiState.Error -> error(state.message)
        is UiState.Success -> success(state.data)
    }
}