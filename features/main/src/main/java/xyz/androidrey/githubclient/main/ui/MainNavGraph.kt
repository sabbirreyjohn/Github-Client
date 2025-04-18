package xyz.androidrey.githubclient.main.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.androidrey.githubclient.common.ui.screen.MainScreen
import xyz.androidrey.githubclient.main.ui.userlist.HomeScreen


@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainScreen.UserList) {
        composable<MainScreen.UserList> {
            HomeScreen(hiltViewModel(), navController = navController)
        }
        composable<MainScreen.Repository> {
//            ProfileScreen(hiltViewModel(), navController = navController){
//
//            }
        }
    }
}