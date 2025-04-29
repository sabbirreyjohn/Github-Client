package xyz.androidrey.githubclient.common.ui.state

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Error(val message: String) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}