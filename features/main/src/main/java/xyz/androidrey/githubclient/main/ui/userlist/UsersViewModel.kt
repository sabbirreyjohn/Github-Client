package xyz.androidrey.githubclient.main.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val rep: MainRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val searchedUsers = searchQuery.debounce(300).flatMapLatest { query ->
        if (query.isBlank()) {
            flow { emit(rep.getCachedUsers()) }
        } else {
            rep.searchCachedUsers(query)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<User>())

    init {
        getUsers()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun getUsers() {
        _uiState.value = UsersUiState.Loading
        viewModelScope.launch {
            when (val status = rep.getUsersList()) {
                is NetworkResult.Error -> {
                    val offlineList = rep.getCachedUsers()
                    if (offlineList.isNotEmpty()) {
                        _uiState.value = UsersUiState.Success(offlineList)
                    } else {
                        _uiState.value = UsersUiState.Error(status.exception.message!!)
                    }
                }

                is NetworkResult.Success -> {
                    rep.cacheUsers(status.result)
                    _uiState.value = UsersUiState.Success(rep.getCachedUsers())
                }
            }

        }
    }
}