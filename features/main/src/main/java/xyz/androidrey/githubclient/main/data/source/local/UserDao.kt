package xyz.androidrey.githubclient.main.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import xyz.androidrey.githubclient.common.data.entity.user.User
import xyz.androidrey.githubclient.storage.UserDataSource

@Dao
interface UserDao: UserDataSource{
    @Query("select * from USER_TABLE")
    override suspend fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM USER_TABLE WHERE login LIKE '%' || :query || '%'")
    override fun searchUsers(query: String): Flow<List<User>>

}