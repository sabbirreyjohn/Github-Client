package xyz.androidrey.githubclient.main.ui.userrepository

import xyz.androidrey.githubclient.common.data.entity.githubuser.GithubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository


sealed class UserRepositoryUiState{
    data object Loading : UserRepositoryUiState()
    data class Error(val message: String) : UserRepositoryUiState()
    data class Success(val user: GithubUser, val repositories: List<Repository>) : UserRepositoryUiState()
}