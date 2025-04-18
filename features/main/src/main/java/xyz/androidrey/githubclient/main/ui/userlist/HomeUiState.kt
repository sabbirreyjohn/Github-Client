package xyz.androidrey.githubclient.main.ui.userlist

import xyz.androidrey.githubclient.common.data.entity.user.User


sealed class HomeUiState{
    data object Loading : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    data class Success(val teachers: List<User>) : HomeUiState()
}