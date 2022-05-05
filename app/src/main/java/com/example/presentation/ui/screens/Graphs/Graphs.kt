package com.example.presentation.ui.screens.Graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
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

@RootNavGraph
@NavGraph
annotation class BottomNavGraph(
    val start: Boolean = false
)

