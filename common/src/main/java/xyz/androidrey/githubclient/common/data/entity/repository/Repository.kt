package xyz.androidrey.githubclient.common.data.entity.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val id: Int,
    val name: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("html_url") val htmlUrl: String,
    val description: String? = null,
    val fork: Boolean = false,
    val language: String? = null,
    @SerialName("stargazers_count") val stargazersCount: Int
)