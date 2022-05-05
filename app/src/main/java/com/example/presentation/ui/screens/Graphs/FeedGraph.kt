package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.ui.screens.FeedScreen.Feed
import com.example.presentation.ui.screens.FeedScreen.FeedViewModel
import com.example.presentation.ui.screens.navHost.FeedComposable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@BottomNavGraph
@NavGraph
annotation class FeedNavGraph(
    val start: Boolean = false
)


@FeedNavGraph(start = true)
@Destination
@Composable
fun FeedDes(viewModel: FeedViewModel = hiltViewModel()) {
    Feed(navigateFeedComposable = {})
}
@FeedNavGraph
@Destination
@Composable
fun FeedSecondDes() {
    FeedComposable()
}