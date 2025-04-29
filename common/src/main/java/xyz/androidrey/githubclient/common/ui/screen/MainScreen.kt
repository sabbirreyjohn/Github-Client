package xyz.androidrey.githubclient.common.ui.screen

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
sealed class MainScreen {
    @Serializable
    data object UserList : MainScreen()

    @Serializable
    data class Repository(val name: String) : MainScreen()
}