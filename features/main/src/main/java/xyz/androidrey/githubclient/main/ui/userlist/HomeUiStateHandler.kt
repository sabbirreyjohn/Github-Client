package xyz.androidrey.githubclient.main.ui.userlist

import androidx.compose.runtime.Composable
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.theme.components.ErrorScreen
import xyz.androidrey.githubclient.theme.components.LoadingScreen

@Composable
fun  HomeUiStateHandler(
    state: HomeUiState,
    loading: @Composable () -> Unit = { LoadingScreen() },
    error: @Composable (String) -> Unit = { ErrorScreen(it) },
    success: @Composable (List<User>) -> Unit
) {
    when (state) {
        is HomeUiState.Error -> error(state.message)
        is HomeUiState.Loading -> loading()
        is HomeUiState.Success -> success(state.teachers)
    }
}