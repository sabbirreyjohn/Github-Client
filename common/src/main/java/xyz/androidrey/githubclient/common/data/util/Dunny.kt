package xyz.androidrey.githubclient.common.data.util

import xyz.androidrey.githubclient.common.data.entity.user.User


fun createDummyUser(): User {
    return User(
        avatar_url = "https://example.com/avatar.jpg",
        events_url = "https://example.com/events",
        followers_url = "https://example.com/followers",
        following_url = "https://example.com/following",
        gists_url = "https://example.com/gists",
        gravatar_id = "somegravatarid",
        html_url = "https://example.com/profile",
        id = 12345,
        login = "dummyuser",
        node_id = "someNodeId",
        organizations_url = "https://example.com/orgs",
        received_events_url = "https://example.com/received_events",
        repos_url = "https://example.com/repos",
        site_admin = false,
        starred_url = "https://example.com/starred",
        subscriptions_url = "https://example.com/subscriptions",
        type = "User",
        url = "https://example.com/user",
        user_view_type = "standard"
    )
}