package xyz.androidrey.githubclient.main.data.source.local

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