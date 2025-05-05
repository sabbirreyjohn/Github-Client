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
import xyz.androidrey.githubclient.common.data.entity.githubuser.GithubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.common.ui.state.UiState
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.main.domain.usecase.repository.GetRepositoryUseCase
import xyz.androidrey.githubclient.main.domain.usecase.users.GetUserDetailsUseCase
import xyz.androidrey.githubclient.network.NetworkResult

@HiltViewModel(assistedFactory = UserRepositoryViewModel.Factory::class)
class UserRepositoryViewModel @AssistedInject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getRepositoryUseCase: GetRepositoryUseCase,
    @Assisted private val userName: String
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Pair<GithubUser, List<Repository>>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var loaded = false

    fun load() {
        if (loaded) return
        loaded = true
        getUserDetailsAndRepositories(userName)
    }

    private fun getUserDetailsAndRepositories(name: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val userResult = getUserDetailsUseCase(name)
            val repoResult = getRepositoryUseCase(name)

            if (userResult is NetworkResult.Success && repoResult is NetworkResult.Success) {
                val nonForkedRepos = repoResult.result.filter { !it.fork }
                _uiState.value = UiState.Success(Pair(userResult.result, nonForkedRepos))
            } else {
                val errorMsg = (userResult as? NetworkResult.Error)?.exception?.message
                    ?: (repoResult as? NetworkResult.Error)?.exception?.message
                    ?: "Unknown error"
                _uiState.value = UiState.Error(errorMsg)
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