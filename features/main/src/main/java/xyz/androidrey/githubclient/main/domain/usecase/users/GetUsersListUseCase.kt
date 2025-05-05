package xyz.androidrey.githubclient.main.domain.usecase.users

import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(): NetworkResult<List<User>> = repository.getUsersList()
}