package xyz.androidrey.githubclient.main.ui.userrepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult

@HiltViewModel(assistedFactory = UserRepositoryViewModel.Factory::class)
class UserRepositoryViewModel @AssistedInject constructor(
    private val repo: MainRepository,
    @Assisted private val userName: String
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserRepositoryUiState>(UserRepositoryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var loaded = false

    fun load() {
        if (loaded) return
        loaded = true
        getUserDetailsAndRepositories(userName)
    }

    private fun getUserDetailsAndRepositories(name: String) {
        _uiState.value = UserRepositoryUiState.Loading
        viewModelScope.launch {
            val userResult = repo.getUserDetails(name)
            val repoResult = repo.getRepository(name)

            if (userResult is NetworkResult.Success && repoResult is NetworkResult.Success) {
                val nonForkedRepos = repoResult.result.filter { !it.fork }
                _uiState.value = UserRepositoryUiState.Success(userResult.result, nonForkedRepos)
            } else {
                val errorMsg = (userResult as? NetworkResult.Error)?.exception?.message
                    ?: (repoResult as? NetworkResult.Error)?.exception?.message
                    ?: "Unknown error"
                _uiState.value = UserRepositoryUiState.Error(errorMsg)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(user: String?): UserRepositoryViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            user: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(user) as T
            }
        }

    }
}