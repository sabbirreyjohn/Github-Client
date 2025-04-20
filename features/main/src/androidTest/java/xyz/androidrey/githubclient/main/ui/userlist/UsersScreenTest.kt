package xyz.androidrey.githubclient.main.ui.userlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.androidrey.githubclient.main.data.repository.FakeMainRepositoryImpl
import xyz.androidrey.githubclient.main.data.source.local.createDummyUsers

@RunWith(AndroidJUnit4::class)
class UsersScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: UsersViewModel
    private lateinit var navController: TestNavHostController

    private val dummyUsers = createDummyUsers(1)

    @Before
    fun setUp() {
        viewModel =
            UsersViewModel(FakeMainRepositoryImpl(users = dummyUsers, searchResults = dummyUsers))
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun userList_ui_displaysUserCorrectly() {
        composeTestRule.setContent {
            UserList(
                users = dummyUsers,
                query = viewModel.searchQuery.value,
                viewModel = viewModel,
                onSelect = {}
            )
        }

        composeTestRule.onNodeWithText(dummyUsers.first().login).assertIsDisplayed()
        composeTestRule.onNodeWithText("Tap to view repos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search Users").assertIsDisplayed()
    }

    @Test
    fun userList_showsEmptyState_whenNoUsers() {
        composeTestRule.setContent {
            UserList(
                users = emptyList(),
                query = "",
                viewModel = viewModel,
                onSelect = {}
            )
        }

        composeTestRule.onNodeWithText("No users found").assertIsDisplayed()
    }

    @Test
    fun userCard_click_invokesCallback() {
        var clickedUser: String? = null

        val dummyUser = createDummyUsers(1).first()

        composeTestRule.setContent {
            UserList(
                users = listOf(dummyUser),
                query = "",
                viewModel = viewModel,
                onSelect = { clickedUser = it }
            )
        }

        composeTestRule.onNodeWithText(dummyUser.login).performClick()
        assertEquals(dummyUser.login, clickedUser)
    }
}