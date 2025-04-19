package xyz.androidrey.githubclient.main.domain.repository


import kotlinx.coroutines.flow.Flow
import xyz.androidrey.githubclient.common.data.entity.githubuser.GitHubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.network.NetworkResult

interface MainRepository {


    suspend fun getUsersList(): NetworkResult<List<User>>

    suspend fun getUserDetails(userName: String): NetworkResult<GitHubUser>
    suspend fun getRepository(userName: String): NetworkResult<List<Repository>>

    suspend fun cacheUsers(users: List<User>)

    suspend fun getCachedUsers(): List<User>

    fun searchCachedUsers(query: String): Flow<List<User>>


}