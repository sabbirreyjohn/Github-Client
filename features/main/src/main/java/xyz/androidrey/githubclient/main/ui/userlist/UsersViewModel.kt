package xyz.androidrey.githubclient.main.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val rep: MainRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        _uiState.value = UsersUiState.Loading
        viewModelScope.launch {
            when (val status = rep.getUsersList()) {
                is NetworkResult.Error -> {
                    _uiState.value = UsersUiState.Error(status.exception.message!!)
                }
                is NetworkResult.Success -> {
                    _uiState.value = UsersUiState.Success(status.result)
                }
            }

        }
    }
}