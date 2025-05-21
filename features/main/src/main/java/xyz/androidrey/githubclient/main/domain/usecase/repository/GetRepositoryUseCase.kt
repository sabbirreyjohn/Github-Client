package xyz.androidrey.githubclient.main.domain.usecase.repository

import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.common.domain.model.DomainResult
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(userName: String): DomainResult<List<Repository>> {
        return when (val networkResult = repository.getRepository(userName)) {
            is NetworkResult.Success -> DomainResult.Success(networkResult.result)
            is NetworkResult.Error -> DomainResult.Error(networkResult.exception.message ?: "Unknown error")
        }
    }
}