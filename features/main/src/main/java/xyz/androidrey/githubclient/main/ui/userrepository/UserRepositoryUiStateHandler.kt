package xyz.androidrey.githubclient.main.ui.userrepository

import androidx.compose.runtime.Composable
import xyz.androidrey.githubclient.common.data.entity.githubuser.GitHubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.ui.userlist.UsersUiState
import xyz.androidrey.githubclient.theme.components.ErrorScreen
import xyz.androidrey.githubclient.theme.components.LoadingScreen

@Composable
fun  UserRepositoryUiStateHandler(
    state: UserRepositoryUiState,
    loading: @Composable () -> Unit = { LoadingScreen() },
    error: @Composable (String) -> Unit = { ErrorScreen(it) },
    success: @Composable (GitHubUser, List<Repository>) -> Unit
) {
    when (state) {
        is UserRepositoryUiState.Error -> error(state.message)
        is UserRepositoryUiState.Loading -> loading()
        is UserRepositoryUiState.Success -> success(state.user, state.repositories)
    }
}