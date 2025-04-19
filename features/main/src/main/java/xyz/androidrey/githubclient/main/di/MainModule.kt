package xyz.androidrey.githubclient.main.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.main.data.repository.MainRepositoryImpl
import xyz.androidrey.githubclient.main.data.source.local.TheDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Singleton
    @Provides
    fun provideProfileRepository(profileRepositoryImpl: MainRepositoryImpl): MainRepository {
        return profileRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TheDatabase {
        return Room.databaseBuilder(
            context,
            TheDatabase::class.java,
            "users.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: TheDatabase) = database.userDao

}