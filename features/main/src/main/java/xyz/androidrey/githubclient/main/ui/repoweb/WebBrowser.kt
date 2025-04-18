package xyz.androidrey.githubclient.main.ui.repoweb

import android.content.Context
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.graphics.toArgb
import androidx.core.net.toUri
import xyz.androidrey.githubclient.theme.primaryContainerLight

fun openCustomTab(context: Context, url: String) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor( primaryContainerLight.toArgb())
                .build()
        )
        .setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM)
        .build()

    customTabsIntent.launchUrl(context, url.toUri())
}