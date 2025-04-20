package xyz.androidrey.githubclient.main.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.androidrey.githubclient.common.data.entity.user.User

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class TheDatabaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TheDatabase
    private lateinit var users: List<User>

    @Before
    fun setUp() {
        users = createDummyUsers(5)
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TheDatabase::class.java
        ).allowMainThreadQueries().build()


    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAll_and_getUsers_should_return_same_users() = runTest {
        database.userDao.insertAll(users)
        val result = database.userDao.getUsers()
        assertEquals(users.size, result.size)
        assertTrue(result.containsAll(users))
    }

    @Test
    fun searchUsers_should_return_matching_results() = runTest {
        database.userDao.insertAll(users)
        val result = database.userDao.searchUsers("user1").first()
        assertTrue(result.any { it.login.contains("user1", ignoreCase = true) })
    }

    @Test
    fun searchUsers_should_return_empty_for_non_matching_query() = runTest {
        database.userDao.insertAll(users)
        val result = database.userDao.searchUsers("unknown_query").first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun insertAll_should_replace_existing_user_on_conflict() = runTest {
        val user = users[0]
        database.userDao.insertAll(listOf(user))

        val updatedUser = user.copy(login = "updatedLogin")
        database.userDao.insertAll(listOf(updatedUser))

        val result = database.userDao.getUsers().first { it.id == user.id }
        assertEquals("updatedLogin", result.login)
    }
}