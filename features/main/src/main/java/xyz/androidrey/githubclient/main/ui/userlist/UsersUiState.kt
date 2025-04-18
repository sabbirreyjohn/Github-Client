package xyz.androidrey.githubclient.main.ui.userlist

import xyz.androidrey.githubclient.common.data.entity.user.User


sealed class UsersUiState{
    data object Loading : UsersUiState()
    data class Error(val message: String) : UsersUiState()
    data class Success(val teachers: List<User>) : UsersUiState()
}