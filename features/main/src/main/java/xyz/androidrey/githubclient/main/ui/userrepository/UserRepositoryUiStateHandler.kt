package xyz.androidrey.githubclient.main.ui.userrepository

import androidx.compose.runtime.Composable
import xyz.androidrey.githubclient.common.data.entity.githubuser.GithubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.main.ui.composable.RepositoryLoadingScreen
import xyz.androidrey.githubclient.theme.components.ErrorScreen
import xyz.androidrey.githubclient.theme.components.LoadingScreen

@Composable
fun  UserRepositoryUiStateHandler(
    state: UserRepositoryUiState,
    loading: @Composable () -> Unit = { RepositoryLoadingScreen() },
    error: @Composable (String) -> Unit = { ErrorScreen(it) },
    success: @Composable (GithubUser, List<Repository>) -> Unit
) {
    when (state) {
        is UserRepositoryUiState.Error -> error(state.message)
        is UserRepositoryUiState.Loading -> loading()
        is UserRepositoryUiState.Success -> success(state.user, state.repositories)
    }
}