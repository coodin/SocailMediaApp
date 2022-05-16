package com.example.presentation.ui.screens.Graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.UserProfile
import com.example.presentation.MainActivityViewModel
import com.example.presentation.ui.screens.FeedScreen.Feed
import com.example.presentation.ui.screens.FeedScreen.FeedViewModel
import com.example.presentation.ui.screens.Graphs.destinations.FeedDesDestination
import com.example.presentation.ui.screens.Graphs.destinations.FeedSecondDesDestination
import com.example.utility.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@BottomNavGraph
//@RootNavGraph
@NavGraph
annotation class FeedNavGraph(
    val start: Boolean = false
)


@FeedNavGraph(start = true)
@Destination
@Composable
fun FeedDes(
    navigator: DestinationsNavigator,
    viewModel: FeedViewModel = hiltViewModel(),
    mainActivityViewModel: MainActivityViewModel
) {
    Feed(navigateFeedComposable = { navigator.navigate(FeedSecondDesDestination) })
}

@FeedNavGraph
@Destination
@Composable
fun FeedSecondDes(
    mainActivityViewModel: MainActivityViewModel
) {
    FeedComposable(mainActivityViewModel)
}

@Composable
fun FeedComposable(mainActivityViewModel: MainActivityViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val dataSet = mainActivityViewModel.userState) {
            is State.Success -> {
                Text(text = "The first user email ${dataSet.data?.email}")
            }
        }

    }
}