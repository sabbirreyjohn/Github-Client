package xyz.androidrey.githubclient.main.data.repository

import xyz.androidrey.githubclient.common.data.entity.githubuser.GithubUser
import xyz.androidrey.githubclient.common.data.entity.user.User

fun createDummyUsers(count: Int = 10): List<User> {
    return List(count) { index ->
        User(
            avatar_url = "https://avatars.githubusercontent.com/u/${index + 1}?v=4",
            events_url = "https://api.github.com/users/user$index/events{/privacy}",
            followers_url = "https://api.github.com/users/user$index/followers",
            following_url = "https://api.github.com/users/user$index/following{/other_user}",
            gists_url = "https://api.github.com/users/user$index/gists{/gist_id}",
            gravatar_id = "",
            html_url = "https://github.com/user$index",
            id = index + 1,
            login = "user$index",
            node_id = "MDQ6VXNlcj${index + 1000}",
            organizations_url = "https://api.github.com/users/user$index/orgs",
            received_events_url = "https://api.github.com/users/user$index/received_events",
            repos_url = "https://api.github.com/users/user$index/repos",
            site_admin = index % 2 == 0,
            starred_url = "https://api.github.com/users/user$index/starred{/owner}{/repo}",
            subscriptions_url = "https://api.github.com/users/user$index/subscriptions",
            type = "User",
            url = "https://api.github.com/users/user$index",
            user_view_type = "public"
        )
    }
}

fun createDummyGithubUser(): GithubUser {
    return GithubUser(
        login = "dummyuser",
        id = 12345,
        nodeId = "MDQ6VXNlcjEyMzQ1",
        avatarUrl = "https://avatars.githubusercontent.com/u/12345?v=4",
        gravatarId = "",
        url = "https://api.github.com/users/dummyuser",
        htmlUrl = "https://github.com/dummyuser",
        followersUrl = "https://api.github.com/users/dummyuser/followers",
        followingUrl = "https://api.github.com/users/dummyuser/following{/other_user}",
        gistsUrl = "https://api.github.com/users/dummyuser/gists{/gist_id}",
        starredUrl = "https://api.github.com/users/dummyuser/starred{/owner}{/repo}",
        subscriptionsUrl = "https://api.github.com/users/dummyuser/subscriptions",
        organizationsUrl = "https://api.github.com/users/dummyuser/orgs",
        reposUrl = "https://api.github.com/users/dummyuser/repos",
        eventsUrl = "https://api.github.com/users/dummyuser/events{/privacy}",
        receivedEventsUrl = "https://api.github.com/users/dummyuser/received_events",
        type = "User",
        userViewType = "public",
        siteAdmin = false,
        name = "Dummy User",
        company = "DummyCorp",
        blog = "https://dummy.blog",
        location = "Internet",
        email = "dummy@example.com",
        hireable = true,
        bio = "I am a dummy user for testing.",
        twitterUsername = "dummyuser",
        publicRepos = 12,
        publicGists = 3,
        followers = 100,
        following = 50,
        createdAt = "2020-01-01T00:00:00Z",
        updatedAt = "2024-01-01T00:00:00Z"
    )
}