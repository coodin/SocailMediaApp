package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.ui.screens.Graphs.destinations.HomeDesDestination
import com.example.presentation.ui.screens.Graphs.destinations.SecondScreenDesDestination
import com.example.presentation.ui.screens.HomeScreen.Home
import com.example.presentation.ui.screens.HomeScreen.HomeViewModel
import com.example.presentation.ui.screens.navHost.NewComposable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigateTo

@BottomNavGraph(start = true)
@NavGraph
annotation class HomeNavGraph(
    val start: Boolean = false
)

data class HomeDesNavArgs(
    val id: String? = null
)

@HomeNavGraph(start = true)
@Destination(navArgsDelegate = HomeDesNavArgs::class)
@Composable
fun HomeDes(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Home(
        viewModel = viewModel,
        navigateSecondScreen = { navController.navigateTo(SecondScreenDesDestination) }
    )
}


@HomeNavGraph
@Destination
@Composable
fun SecondScreenDes(
    navController: DestinationsNavigator
) {
    NewComposable(navigate = { navController.navigate(HomeDesDestination()) })
}

