package xyz.androidrey.githubclient.main.domain.usecase.users

import xyz.androidrey.githubclient.common.data.entity.githubuser.GithubUser
import xyz.androidrey.githubclient.common.domain.model.DomainResult
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(userName: String): DomainResult<GithubUser> {
        return when (val networkResult = repository.getUserDetails(userName)) {
            is NetworkResult.Success -> DomainResult.Success(networkResult.result)
            is NetworkResult.Error -> DomainResult.Error(networkResult.exception.message ?: "Unknown error")
        }
    }
}