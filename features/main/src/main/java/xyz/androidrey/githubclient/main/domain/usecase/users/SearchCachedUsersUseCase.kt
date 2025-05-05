package xyz.androidrey.githubclient.main.domain.usecase.users

import kotlinx.coroutines.flow.Flow
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import javax.inject.Inject

class SearchCachedUsersUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(query: String): Flow<List<User>> = repository.searchCachedUsers(query)
}