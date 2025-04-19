package xyz.androidrey.githubclient.main.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.androidrey.githubclient.common.BuildConfig
import xyz.androidrey.githubclient.common.data.entity.githubuser.GithubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.data.source.local.UserDao
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult
import xyz.androidrey.githubclient.network.http.RequestHandler
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler,
    private val userDao: UserDao
) : MainRepository {
    override suspend fun getUsersList(): NetworkResult<List<User>> {
        requestHandler.setBearerToken(BuildConfig.access_token)
        return requestHandler.get<List<User>>(
            urlPathSegments = listOf("users"),
            queryParams = mapOf("since" to "0", "per_page" to "50")
        )
    }

    override suspend fun getUsersListWithCache(): NetworkResult<List<User>> {
        requestHandler.setBearerToken(BuildConfig.access_token)
        return when (val result = requestHandler.get<List<User>>(
            urlPathSegments = listOf("users"),
            queryParams = mapOf("since" to "0", "per_page" to "50")
        )) {
            is NetworkResult.Success -> {
                cacheUsers(result.result)
                val cached = getCachedUsers()
                NetworkResult.Success(cached)
            }

            is NetworkResult.Error -> {
                val cached = getCachedUsers()
                if (cached.isNotEmpty()) {
                    NetworkResult.Success(cached)
                } else {
                    result
                }
            }
        }
    }

    override suspend fun getUserDetails(userName: String): NetworkResult<GithubUser> {
        requestHandler.setBearerToken(BuildConfig.access_token)
        return requestHandler.get<GithubUser>(
            urlPathSegments = listOf("users", userName)
        )
    }

    override suspend fun getRepository(userName: String): NetworkResult<List<Repository>> {
        requestHandler.setBearerToken(BuildConfig.access_token)
        return requestHandler.get<List<Repository>>(
            urlPathSegments = listOf("users", userName, "repos")
        )
    }

    override suspend fun cacheUsers(users: List<User>) {
        userDao.insertAll(users)
    }

    override suspend fun getCachedUsers(): List<User> {
        return userDao.getUsers()

    }

    override fun searchCachedUsers(query: String): Flow<List<User>> {
        return userDao.searchUsers(query)
    }

}