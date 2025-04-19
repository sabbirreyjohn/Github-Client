package xyz.androidrey.githubclient.main.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.androidrey.githubclient.common.data.entity.user.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class TheDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}