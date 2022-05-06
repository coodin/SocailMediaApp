package com.example.presentation.ui.screens.navHost


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.presentation.ui.screens.HomeScreen.Home
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.presentation.ui.screens.CheatScreen
import com.example.presentation.ui.screens.FeedScreen.Feed
import com.example.presentation.ui.screens.HomeScreen.HomeViewModel

import com.example.presentation.ui.screens.FavortieScreen.Favorite
import com.example.presentation.ui.screens.FavortieScreen.FavoriteViewModel
import com.example.presentation.ui.screens.ProfileScreen.ProfileScreen
import com.example.presentation.ui.screens.ProfileScreen.ProfileViewModel
import com.example.presentation.ui.screens.ProfileScreen.SettingsScreen


//fun NavGraphBuilder.bottomNavigation(navController: NavController, userID: String) {
//    navigation(
//        startDestination = NavigationRoutes.HomeRoute.route,
////        arguments = listOf(navArgument("ID") {
////            type = NavType.StringType
////            defaultValue = "No User"
////        }),
//        route = NavigationRoutes.BottomRoute.route
//    ) {
//        home(navController, userID)
//        favorite(navController, userID)
//        feed(navController, userID)
//        profile(navController, userID)
//    }
//}


fun NavGraphBuilder.home(navController: NavController) {
    navigation(
        startDestination = NavigationRoutes.Home.route,
        arguments = listOf(navArgument("ID") {
            type = NavType.StringType
            defaultValue = "NoUser"
        }),
        route = NavigationRoutes.HomeRoute.route
    ) {
        composable(
            NavigationRoutes.Home.route
            // I don't need to specify arguments one more time to get arguments from navigation due to
            // navigation()'s arguments parameter has been given by me.
//            arguments = listOf(navArgument("ID") {
//                type = NavType.StringType
//            })
        ) {
//            parentEntry = remember {
//                navController.getBackStackEntry(BOTTOM_ROUTE)
//            }
//            val mainViewModel = hiltViewModel<HomeViewModel>(
//                //parentEntry!!
//            )
            val sharedViewModel = hiltViewModel<HomeViewModel>()
            Home(
                viewModel = sharedViewModel,
                navigateSecondScreen = {
                    navController.navigate("NewComposable")
                }
            )
        }
        composable("NewComposable") {
            NewComposable(navigate = {})
        }
    }
}

@Composable
fun NewComposable(navigate: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Go back to Login Screen")
    }
}


fun NavGraphBuilder.favorite(navController: NavController) {
    navigation(
        startDestination = NavigationRoutes.Fav.route,
        route = NavigationRoutes.FavRoute.route
    ) {
        composable(NavigationRoutes.Fav.route) {

            val favoriteViewModel = hiltViewModel<FavoriteViewModel>(
                //parentEntry!!
            )
            Favorite(favoriteViewModel, navigateChatScreen = {
                navController.navigate(NavigationRoutes.ChatScreen.createRoute()) {
//                    popUpTo(FAVORITE_ROUTE) {
//                        saveState = true
//                    }
//                    // Avoid multiple copies of the same destination when
//                    // reselecting the same item
//                    launchSingleTop = true
//                    // Restore state when reselecting a previously selected item
//                    restoreState = true
                }
            })
        }

        composable(NavigationRoutes.ChatScreen.route) {
            CheatScreen()
        }
    }

}


fun NavGraphBuilder.feed(navController: NavController) {
    navigation(
        startDestination = NavigationRoutes.Feed.route,
        route = NavigationRoutes.FeedRoute.route
    ) {
        composable(NavigationRoutes.Feed.route) { Feed { navController.navigate("FeedComposable") } }
        composable("FeedComposable") {
            FeedComposable()
        }
    }
}

@Composable
fun FeedComposable() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Hello FeedComposable")
    }
}

fun NavGraphBuilder.profile(navController: NavController) {
    navigation(
        startDestination = NavigationRoutes.Profile.route,
        route = NavigationRoutes.ProfileRoute.route
    ) {
        composable(NavigationRoutes.Profile.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(viewModel) { navController.navigate(NavigationRoutes.ProfileSettings.createRoute()) }
        }
        composable(NavigationRoutes.ProfileSettings.route) {
//            SettingsScreen(navigateBackProfile = {
//                navController.navigateUp()
//            }, viewModel = )
        }
    }
}

@Composable
fun ProfileComposable() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Hello ProfileComposable")
    }
}