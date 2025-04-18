package xyz.androidrey.githubclient

import android.app.Application
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}