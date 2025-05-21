package xyz.androidrey.githubclient.main.ui.userlist

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import xyz.androidrey.githubclient.common.domain.model.DomainResult
import xyz.androidrey.githubclient.common.ui.state.UiState
import xyz.androidrey.githubclient.main.data.repository.createDummyUsers
import xyz.androidrey.githubclient.main.domain.usecase.users.GetCachedUsersUseCase
import xyz.androidrey.githubclient.main.domain.usecase.users.GetUsersListWithCacheUseCase
import xyz.androidrey.githubclient.main.domain.usecase.users.SearchCachedUsersUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    private lateinit var getCachedUsersUseCase: GetCachedUsersUseCase
    private lateinit var searchCachedUsersUseCase: SearchCachedUsersUseCase
    private lateinit var getUsersListWithCacheUseCase: GetUsersListWithCacheUseCase


    private lateinit var viewModel: UsersViewModel

    private val testDispatcher = StandardTestDispatcher()

    private var users = createDummyUsers(3)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCachedUsersUseCase = mockk()
        searchCachedUsersUseCase = mockk()
        getUsersListWithCacheUseCase = mockk()

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun `initial uiState should emit Success when API returns users`() = runTest {
        // Arrange
        coEvery { getUsersListWithCacheUseCase() } returns DomainResult.Success(users)
        coEvery { getCachedUsersUseCase() } returns users
        coEvery { searchCachedUsersUseCase(any()) } returns flowOf(users)

        // Act
        viewModel = UsersViewModel(
            getCachedUsersUseCase,
            searchCachedUsersUseCase,
            getUsersListWithCacheUseCase
        )
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals(users, (state as UiState.Success).data)
    }

    @Test
    fun `initial uiState should emit Error when API fails and no cache`() = runTest {
        coEvery { getUsersListWithCacheUseCase() } returns DomainResult.Error("Network failed")
        coEvery { getCachedUsersUseCase() } returns emptyList()
        coEvery { searchCachedUsersUseCase(any()) } returns flowOf(emptyList())

        viewModel = UsersViewModel(
            getCachedUsersUseCase,
            searchCachedUsersUseCase,
            getUsersListWithCacheUseCase
        )
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
        assertEquals("Network failed", (state as UiState.Error).message)
    }

    @Test
    fun `updateSearchQuery should change searchQuery value`() = runTest {
        coEvery { getUsersListWithCacheUseCase() } returns DomainResult.Success(users)
        coEvery { getCachedUsersUseCase() } returns users
        coEvery { searchCachedUsersUseCase(any()) } returns flowOf(users)

        viewModel = UsersViewModel(
            getCachedUsersUseCase,
            searchCachedUsersUseCase,
            getUsersListWithCacheUseCase
        )
        viewModel.updateSearchQuery("android")

        assertEquals("android", viewModel.searchQuery.value)
    }

    @Test
    fun `searchedUsers should emit filtered users when query is not blank`() = runTest {
        val filtered = users.filter { it.login.contains("dev") }

        coEvery { getUsersListWithCacheUseCase() } returns DomainResult.Success(users)
        coEvery { getCachedUsersUseCase() } returns users
        coEvery { searchCachedUsersUseCase("dev") } returns flowOf(filtered)

        viewModel = UsersViewModel(
            getCachedUsersUseCase,
            searchCachedUsersUseCase,
            getUsersListWithCacheUseCase
        )
        viewModel.updateSearchQuery("dev")
        advanceTimeBy(400)

        val result = viewModel.searchedUsers.value
        assertEquals(filtered, result)
    }

}