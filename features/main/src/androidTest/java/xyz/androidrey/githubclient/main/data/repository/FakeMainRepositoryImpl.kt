package xyz.androidrey.githubclient.main.data.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import xyz.androidrey.githubclient.common.data.entity.githubuser.GithubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkException
import xyz.androidrey.githubclient.network.NetworkResult

class FakeMainRepositoryImpl(
    private val users: List<User> = emptyList(),
    private val searchResults: List<User> = emptyList(),
    private val shouldFail: Boolean = false
) : MainRepository {

    override suspend fun getUsersList(): NetworkResult<List<User>> {
        return if (shouldFail) NetworkResult.Error(
            null,
            NetworkException.UnknownException("Fake error", Throwable())
        )
        else NetworkResult.Success(users)
    }

    override suspend fun getUsersListWithCache(): NetworkResult<List<User>> {
        return getUsersList()
    }

    override suspend fun getCachedUsers(): List<User> {
        return users
    }

    override fun searchCachedUsers(query: String): Flow<List<User>> {
        return flowOf(searchResults)
    }

    override suspend fun cacheUsers(users: List<User>) {
        // No-op
    }

    override suspend fun getUserDetails(userName: String): NetworkResult<GithubUser> {
        throw NotImplementedError("Not needed for this test")
    }

    override suspend fun getRepository(userName: String): NetworkResult<List<Repository>> {
        throw NotImplementedError("Not needed for this test")
    }
}