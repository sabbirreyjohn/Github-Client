package xyz.androidrey.githubclient.main.data.repository

import xyz.androidrey.githubclient.common.BuildConfig
import xyz.androidrey.githubclient.common.data.entity.githubuser.GitHubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkResult
import xyz.androidrey.githubclient.network.http.RequestHandler
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val requestHandler: RequestHandler) :
    MainRepository {
    override suspend fun getUsersList(): NetworkResult<List<User>> {
        requestHandler.setBearerToken(BuildConfig.access_token)
        return requestHandler.get<List<User>>(
            urlPathSegments = listOf("users"),
            queryParams = mapOf("since" to "0", "per_page" to "10")
        )
    }

    override suspend fun getUserDetails(userName: String): NetworkResult<GitHubUser> {
        requestHandler.setBearerToken(BuildConfig.access_token)
        return requestHandler.get<GitHubUser>(
            urlPathSegments = listOf("users", userName)
        )
    }

    override suspend fun getRepository(userName: String): NetworkResult<List<Repository>> {
        requestHandler.setBearerToken(BuildConfig.access_token)
        return requestHandler.get<List<Repository>>(
            urlPathSegments = listOf("users", userName, "repos")
        )
    }

}