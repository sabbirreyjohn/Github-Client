package xyz.androidrey.githubclient.main.domain.usecase.users

import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import javax.inject.Inject

class CacheUsersUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(users: List<User>) = repository.cacheUsers(users)
}