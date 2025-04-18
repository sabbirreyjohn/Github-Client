package xyz.androidrey.githubclient.storage

import kotlinx.coroutines.flow.Flow

data class CurrentUser(val email: String, val accessToken: String)

interface SessionHandler {
    suspend fun setCurrentUser(email: String, accessToken: String)
    suspend fun getCurrentUser(): Flow<CurrentUser>
    suspend fun clearCurrentUser()
}