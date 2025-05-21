package xyz.androidrey.githubclient.main.domain.usecase.users

import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.common.domain.model.DomainResult
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult
import javax.inject.Inject

class GetUsersListWithCacheUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(): DomainResult<List<User>> {
        return when (val networkResult = repository.getUsersListWithCache()) {
            is NetworkResult.Success -> DomainResult.Success(networkResult.result)
            is NetworkResult.Error -> DomainResult.Error(networkResult.exception.message ?: "Unknown error")
        }
    }
}