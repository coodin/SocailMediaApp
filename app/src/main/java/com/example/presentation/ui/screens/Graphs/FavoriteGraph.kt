package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.MainActivityViewModel
import com.example.presentation.ui.screens.CheatScreen
import com.example.presentation.ui.screens.FavortieScreen.Favorite
import com.example.presentation.ui.screens.FavortieScreen.FavoriteViewModel
import com.example.presentation.ui.screens.Graphs.destinations.CheatDesDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigateTo

@BottomNavGraph
//@RootNavGraph
@NavGraph
annotation class FavoriteNavGraph(
    val start: Boolean = false
)

@FavoriteNavGraph(start = true)
@Destination
@Composable
fun FavoriteDes(
    navigator: DestinationsNavigator,
    viewModel: FavoriteViewModel = hiltViewModel(),
    mainActivityViewModel: MainActivityViewModel
) {
    Favorite(
        favoriteViewModel = viewModel,
        navigateChatScreen = { navigator.navigate(CheatDesDestination) }
    )
}

// I had extracted  this destination from graph because I didn't want to be seen bottom navbar after I
// navigated to this destination.
//@FavoriteNavGraph
@Destination
@Composable
fun CheatDes(
    mainActivityViewModel: MainActivityViewModel
) {
    CheatScreen()
}