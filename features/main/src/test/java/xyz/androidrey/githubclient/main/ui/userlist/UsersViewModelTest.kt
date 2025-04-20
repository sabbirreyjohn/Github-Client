package xyz.androidrey.githubclient.main.ui.userlist

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import xyz.androidrey.githubclient.main.data.repository.createDummyUsers
import xyz.androidrey.githubclient.main.domain.repository.MainRepository
import xyz.androidrey.githubclient.network.NetworkException
import xyz.androidrey.githubclient.network.NetworkResult
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    private lateinit var repository: MainRepository

    private lateinit var viewModel: UsersViewModel

    private val testDispatcher = StandardTestDispatcher()

    private var users = createDummyUsers(3)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun `initial uiState should emit Success when API returns users`() = runTest {
        // Arrange
        coEvery { repository.getUsersListWithCache() } returns NetworkResult.Success(users)
        coEvery { repository.getCachedUsers() } returns users
        coEvery { repository.searchCachedUsers(any()) } returns flowOf(users)

        // Act
        viewModel = UsersViewModel(repository)
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersUiState.Success)
        assertEquals(users, (state as UsersUiState.Success).users)
    }

    @Test
    fun `initial uiState should emit Error when API fails and no cache`() = runTest {
        coEvery { repository.getUsersListWithCache() } returns NetworkResult.Error(
            exception = NetworkException.UnknownException("Network failed", Throwable()),
            body = "Network failed"
        )
        coEvery { repository.getCachedUsers() } returns emptyList()
        coEvery { repository.searchCachedUsers(any()) } returns flowOf(emptyList())

        viewModel = UsersViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UsersUiState.Error)
        assertEquals("Network failed", (state as UsersUiState.Error).message)
    }

    @Test
    fun `updateSearchQuery should change searchQuery value`() = runTest {
        coEvery { repository.getUsersListWithCache() } returns NetworkResult.Success(users)
        coEvery { repository.getCachedUsers() } returns users
        coEvery { repository.searchCachedUsers(any()) } returns flowOf(users)

        viewModel = UsersViewModel(repository)
        viewModel.updateSearchQuery("android")

        assertEquals("android", viewModel.searchQuery.value)
    }

    @Test
    fun `searchedUsers should emit filtered users when query is not blank`() = runTest {
        val filtered = users.filter { it.login.contains("dev") }

        coEvery { repository.getUsersListWithCache() } returns NetworkResult.Success(users)
        coEvery { repository.getCachedUsers() } returns users
        coEvery { repository.searchCachedUsers("dev") } returns flowOf(filtered)

        viewModel = UsersViewModel(repository)
        viewModel.updateSearchQuery("dev")
        advanceTimeBy(400)

        val result = viewModel.searchedUsers.value
        assertEquals(filtered, result)
    }

}