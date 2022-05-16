package com.example.presentation.ui.screens.Graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.presentation.MainActivityViewModel
import com.example.presentation.ui.screens.ProfileScreen.ProfileViewModel
import com.example.presentation.ui.screens.SplashScreen.AnimatedSplashScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.NavGraphSpec

enum class BottomBarDestination(
    val graph: NavGraphSpec,
    val icon: ImageVector,
    //@StringRes val label: Int
) {
    Home(NavGraphs.home, Icons.Default.Home),
    Favorite(NavGraphs.favorite, Icons.Default.Send),
    Feed(NavGraphs.feed, Icons.Default.FavoriteBorder),
    Profile(NavGraphs.profile, Icons.Default.PersonOutline)
}

@RootNavGraph()
@NavGraph
annotation class BottomNavGraph(
    val start: Boolean = false
)

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashDes(navigator:DestinationsNavigator,viewModel: MainActivityViewModel) {
    AnimatedSplashScreen(navigator = navigator,viewModel)
}


