package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphNavigator
import com.example.presentation.ui.screens.Graphs.destinations.SettingsDesDestination
import com.example.presentation.ui.screens.ProfileScreen.ProfileScreen
import com.example.presentation.ui.screens.ProfileScreen.ProfileViewModel
import com.example.presentation.ui.screens.ProfileScreen.SettingsScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@BottomNavGraph
@NavGraph
annotation class ProfileNavGraph(
    val start: Boolean = false
)

@ProfileNavGraph(start = true)
@Destination
@Composable
fun ProfileDes(navigator: DestinationsNavigator, viewModel: ProfileViewModel = hiltViewModel()) {
    ProfileScreen(
        viewModel = viewModel,
        navigateProfileComposable = { navigator.navigate(SettingsDesDestination) }
    )
}

//@ProfileNavGraph
@Destination
@Composable
fun SettingsDes(navigator: DestinationsNavigator) {
    SettingsScreen(navigateBackProfile = { navigator.navigateUp() })
}