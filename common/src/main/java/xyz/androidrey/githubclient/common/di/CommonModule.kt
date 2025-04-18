package xyz.androidrey.githubclient.common.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import xyz.androidrey.githubclient.common.BuildConfig
import xyz.androidrey.githubclient.network.http.HttpClientBuilder
import xyz.androidrey.githubclient.network.http.RequestHandler
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CommonModule {


    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClientBuilder().protocol(URLProtocol.HTTPS).host(BuildConfig.base_url).build()

    @Provides
    @Singleton
    fun provideRequestHandler(client: HttpClient) = RequestHandler(client)

}