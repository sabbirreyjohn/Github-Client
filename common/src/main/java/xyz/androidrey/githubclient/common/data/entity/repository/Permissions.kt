package xyz.androidrey.githubclient.common.data.entity.repository

import kotlinx.serialization.Serializable

@Serializable
data class Permissions(
    val admin: Boolean,
    val maintain: Boolean,
    val pull: Boolean,
    val push: Boolean,
    val triage: Boolean
)