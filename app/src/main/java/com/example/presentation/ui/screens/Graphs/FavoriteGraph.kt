package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.ui.screens.CheatScreen
import com.example.presentation.ui.screens.FavortieScreen.Favorite
import com.example.presentation.ui.screens.FavortieScreen.FavoriteViewModel
import com.example.presentation.ui.screens.Graphs.destinations.CheatDesDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigateTo

@BottomNavGraph
@NavGraph
annotation class FavoriteNavGraph(
    val start: Boolean = false
)

@FavoriteNavGraph(start = true)
@Destination
@Composable
fun FavoriteDes(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    Favorite(
        favoriteViewModel = viewModel,
        navigateChatScreen = { navController.navigateTo(CheatDesDestination) }
    )
}

@FavoriteNavGraph
@Destination
@Composable
fun CheatDes() {
    CheatScreen()
}