package xyz.androidrey.githubclient.main.ui.userlistwithdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import xyz.androidrey.githubclient.main.ui.userlist.HomeUiStateHandler
import xyz.androidrey.githubclient.main.ui.userlist.UserList
import xyz.androidrey.githubclient.main.ui.userlist.UsersViewModel
import xyz.androidrey.githubclient.main.ui.userrepository.UserRepositoryScreen
import xyz.androidrey.githubclient.main.ui.userrepository.UserRepositoryViewModel
import xyz.androidrey.githubclient.theme.components.AppBar

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun UserListWithDetailsScreen(viewModel: UsersViewModel) {
    val usersState by viewModel.uiState.collectAsState()

    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<String>()
    val scope = rememberCoroutineScope()
    val currentUser = scaffoldNavigator.currentDestination?.contentKey
    Column {
        AppBar(title = if (currentUser != null) "$currentUser's Repositories" else "GitHub Client")
        NavigableListDetailPaneScaffold(
            navigator = scaffoldNavigator,
            listPane = {

                AnimatedPane {

                    HomeUiStateHandler(usersState) { users ->
                        UserList(users = users) { login ->
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
                    scaffoldNavigator.currentDestination?.contentKey?.let {
                        val viewModel =
                            hiltViewModel<UserRepositoryViewModel, UserRepositoryViewModel.Factory>(
                                key = it,
                                creationCallback = { factory -> factory.create(it) }
                            )
                        UserRepositoryScreen(
                            userName = it,
                            viewModel = viewModel,
                            navController = rememberNavController()
                        )

                    }?: Text(text = "No user selected")
                }
            },
        )
    }
}