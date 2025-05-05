package xyz.androidrey.githubclient.main.domain.usecase.users

import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import javax.inject.Inject

class GetCachedUsersUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(): List<User> = repository.getCachedUsers()
}