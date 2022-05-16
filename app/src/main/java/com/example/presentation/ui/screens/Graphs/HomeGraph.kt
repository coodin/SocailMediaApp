package com.example.presentation.ui.screens.Graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.MainActivityViewModel
import com.example.presentation.ui.screens.Graphs.destinations.HomeDesDestination
import com.example.presentation.ui.screens.Graphs.destinations.LoginDesDestination
import com.example.presentation.ui.screens.Graphs.destinations.SecondScreenDesDestination
import com.example.presentation.ui.screens.HomeScreen.Home
import com.example.presentation.ui.screens.HomeScreen.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigateTo

@BottomNavGraph(start = true)
//@RootNavGraph
@NavGraph
annotation class HomeNavGraph(
    val start: Boolean = false
)

data class HomeDesNavArgs(
    val id: String? = null
)

@HomeNavGraph(start = true)
@Destination()
@Composable
fun HomeDes(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
    mainActivityViewModel: MainActivityViewModel
) {
    Home(
        viewModel = viewModel,
        navigateSecondScreen = { navigator.navigate(SecondScreenDesDestination) },
        mainActivityViewModel = mainActivityViewModel
    )
}


@HomeNavGraph
@Destination
@Composable
fun SecondScreenDes(
    navigator: DestinationsNavigator,
    mainActivityViewModel: MainActivityViewModel
) {
    NewComposable(navigate = { navigator.navigate(HomeDesDestination()) })
}

@Composable
fun NewComposable(navigate: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Go back to Login Screen")
    }
}

