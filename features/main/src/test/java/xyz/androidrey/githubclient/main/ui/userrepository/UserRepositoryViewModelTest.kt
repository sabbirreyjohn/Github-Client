package xyz.androidrey.githubclient.main.ui.userrepository

import androidx.lifecycle.ViewModelProvider
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import xyz.androidrey.githubclient.common.data.entity.githubuser.GithubUser
import xyz.androidrey.githubclient.common.data.entity.repository.Repository
import xyz.androidrey.githubclient.common.domain.model.DomainResult
import xyz.androidrey.githubclient.common.ui.state.UiState
import xyz.androidrey.githubclient.main.domain.usecase.repository.GetRepositoryUseCase
import xyz.androidrey.githubclient.main.domain.usecase.users.GetUserDetailsUseCase

@ExperimentalCoroutinesApi
class UserRepositoryViewModelTest {

    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
    private lateinit var getRepositoryUseCase: GetRepositoryUseCase
    private lateinit var viewModel: UserRepositoryViewModel
    private lateinit var viewModelFactory: UserRepositoryViewModel.Factory

    private val testDispatcher = StandardTestDispatcher()
    private val testUserName = "testUser"

    private val dummyUser = GithubUser(login = testUserName, id = 1, avatarUrl = "", name = "Test User", followers = 0, following = 0)
    private val dummyRepos = listOf(
        Repository(id = 1, name = "Repo1", description = "Desc1", stargazersCount = 10, language = "Kotlin", htmlUrl = "", fork = false),
        Repository(id = 2, name = "Repo2", description = "Desc2", stargazersCount = 20, language = "Java", htmlUrl = "", fork = true) // Forked repo
    )
    private val nonForkedRepos = dummyRepos.filter { !it.fork }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUserDetailsUseCase = mockk()
        getRepositoryUseCase = mockk()
        // Mock the AssistedFactory for UserRepositoryViewModel
        viewModelFactory = mockk {
            coEvery { create(testUserName) } answers {
                UserRepositoryViewModel(getUserDetailsUseCase, getRepositoryUseCase, testUserName)
            }
        }
        viewModel = viewModelFactory.create(testUserName)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load WHEN use cases return success THEN uiState is Success with user and non-forked repos`() = runTest {
        coEvery { getUserDetailsUseCase(testUserName) } returns DomainResult.Success(dummyUser)
        coEvery { getRepositoryUseCase(testUserName) } returns DomainResult.Success(dummyRepos)

        viewModel.load() // Trigger loading
        advanceUntilIdle() // Let coroutines complete

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals(dummyUser, (state as UiState.Success).data.first)
        assertEquals(nonForkedRepos, state.data.second)
    }

    @Test
    fun `load WHEN getUserDetailsUseCase returns error THEN uiState is Error`() = runTest {
        val errorMessage = "Failed to fetch user"
        coEvery { getUserDetailsUseCase(testUserName) } returns DomainResult.Error(errorMessage)
        coEvery { getRepositoryUseCase(testUserName) } returns DomainResult.Success(dummyRepos)

        viewModel.load()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
        assertEquals(errorMessage, (state as UiState.Error).message)
    }

    @Test
    fun `load WHEN getRepositoryUseCase returns error THEN uiState is Error`() = runTest {
        val errorMessage = "Failed to fetch repositories"
        coEvery { getUserDetailsUseCase(testUserName) } returns DomainResult.Success(dummyUser)
        coEvery { getRepositoryUseCase(testUserName) } returns DomainResult.Error(errorMessage)

        viewModel.load()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
        assertEquals(errorMessage, (state as UiState.Error).message)
    }
    
    @Test
    fun `load WHEN both use cases return error THEN uiState is Error with user error message`() = runTest {
        val userErrorMessage = "User fetch failed"
        val repoErrorMessage = "Repo fetch failed"
        coEvery { getUserDetailsUseCase(testUserName) } returns DomainResult.Error(userErrorMessage)
        coEvery { getRepositoryUseCase(testUserName) } returns DomainResult.Error(repoErrorMessage)

        viewModel.load()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
        assertEquals(userErrorMessage, (state as UiState.Error).message) // viewModel prioritizes user error
    }

    @Test
    fun `load multiple times only fetches once`() = runTest {
        coEvery { getUserDetailsUseCase(testUserName) } returns DomainResult.Success(dummyUser)
        coEvery { getRepositoryUseCase(testUserName) } returns DomainResult.Success(dummyRepos)

        viewModel.load()
        viewModel.load() // Call load again
        advanceUntilIdle()
        
        verify(exactly = 1) { getUserDetailsUseCase(testUserName) }
        verify(exactly = 1) { getRepositoryUseCase(testUserName) }
    }
    
    @Test
    fun `initial state is Loading before load is called and completes`() = runTest {
         // Re-create ViewModel for this specific test to check initial state before load() is manually called
         val freshViewModel = viewModelFactory.create(testUserName)
         assertTrue(freshViewModel.uiState.value is UiState.Loading) // State right after creation

         // Mock calls for the subsequent load
         coEvery { getUserDetailsUseCase(testUserName) } returns DomainResult.Success(dummyUser)
         coEvery { getRepositoryUseCase(testUserName) } returns DomainResult.Success(dummyRepos)
         
         freshViewModel.load()
         // Before advanceUntilIdle, it might still be Loading or already moved to Success quickly
         // Depending on dispatcher, it could be Loading or Success.
         // For more deterministic check of intermediate Loading state:
         // val intermediateState = freshViewModel.uiState.value 
         // assertTrue(intermediateState is UiState.Loading) // This might be true if load() sets it synchronously

         advanceUntilIdle() // Let load complete
         assertTrue(freshViewModel.uiState.value is UiState.Success)
    }
}
