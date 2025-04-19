package xyz.androidrey.githubclient.theme.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import xyz.androidrey.githubclient.theme.onPrimaryLight
import xyz.androidrey.githubclient.theme.primaryContainerLight
import xyz.androidrey.githubclient.theme.primaryLight
import xyz.androidrey.githubclient.theme.secondaryLight
import xyz.androidrey.githubclient.theme.tertiaryContainerLight
import xyz.androidrey.githubclient.theme.tertiaryLight

@Composable
fun AppBar(
    title: String,
    navIcon: ImageVector? = null,
    onNav: () -> Unit = {},
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = tertiaryContainerLight,
            titleContentColor = onPrimaryLight,
        ),
        title = {
            Text(text = title)
        },
        navigationIcon = {
            navIcon?.let {
                IconButton(onClick = { onNav() }) {
                    Icon(navIcon, contentDescription = "Nav Icon")
                }
            }
        },
    )
}
