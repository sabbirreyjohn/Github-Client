package xyz.androidrey.githubclient.main.ui.userlistwithdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import xyz.androidrey.githubclient.main.ui.userlist.HomeUiStateHandler
import xyz.androidrey.githubclient.main.ui.userlist.UserList
import xyz.androidrey.githubclient.main.ui.userlist.UsersViewModel
import xyz.androidrey.githubclient.main.ui.userrepository.UserRepositoryScreen
import xyz.androidrey.githubclient.main.ui.userrepository.UserRepositoryViewModel
import xyz.androidrey.githubclient.theme.components.AppBar
import xyz.androidrey.githubclient.theme.components.placeholder.EmptyDetailPlaceholder

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun UserListWithDetailsScreen(viewModel: UsersViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val searchedUsers by viewModel.searchedUsers.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>()
    val scope = rememberCoroutineScope()
    val currentUser = rememberSaveable { mutableStateOf<String?>(null) }

    val isNavigatedFromList =
        scaffoldNavigator.currentDestination?.pane == ListDetailPaneScaffoldRole.List

    LaunchedEffect(isNavigatedFromList) {
        if (isNavigatedFromList && currentUser.value == null) {
            currentUser.value = null
        }
    }
    Column {
        AppBar(title = if (currentUser.value != null) "${currentUser.value}'s Repos" else "GitHub Client")
        NavigableListDetailPaneScaffold(
            navigator = scaffoldNavigator,
            listPane = {
                AnimatedPane {
                    HomeUiStateHandler(uiState) { users ->
                        val userList = if (query.isBlank()) users else searchedUsers
                        UserList(users = userList, query = query, viewModel = viewModel) { login ->
                            currentUser.value = login
                            scope.launch {
                                scaffoldNavigator.navigateTo(
                                    ListDetailPaneScaffoldRole.Detail,
                                    login
                                )
                            }
                        }
                    }
                }
            },
            detailPane = {
                AnimatedPane {
                    val pane = scaffoldNavigator.currentDestination?.pane
                    val userName = currentUser.value
                    if (pane == ListDetailPaneScaffoldRole.Detail && userName != null) {
                        val viewModel =
                            hiltViewModel<UserRepositoryViewModel, UserRepositoryViewModel.Factory>(
                                key = userName,
                                creationCallback = { factory -> factory.create(userName) }
                            )

                        UserRepositoryScreen(
                            userName = userName,
                            viewModel = viewModel,
                            navController = rememberNavController()
                        )
                    } else {
                        EmptyDetailPlaceholder(
                            "No user selected",
                            "Tap on a user from the list to see their repositories.",

                            )
                    }
                }
            },
        )
    }
}