package xyz.androidrey.githubclient.storage

import kotlinx.coroutines.flow.Flow
import xyz.androidrey.githubclient.common.data.entity.user.User


interface UserDataSource {
    suspend fun getUsers(): List<User>
    suspend fun insertAll(users: List<User>)
    fun searchUsers(query: String): Flow<List<User>>
}