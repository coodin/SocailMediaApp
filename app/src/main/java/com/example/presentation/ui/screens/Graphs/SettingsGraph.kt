package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import com.example.presentation.MainActivityViewModel
import com.example.presentation.ui.screens.Graphs.destinations.ChangePasswordDesDestination
import com.example.presentation.ui.screens.Graphs.destinations.EditProfileDesDestination
import com.example.presentation.ui.screens.ProfileScreen.ChangePassword
import com.example.presentation.ui.screens.ProfileScreen.EditProfile
import com.example.presentation.ui.screens.settingsScreen.SettingsScreen
import com.example.presentation.ui.screens.settingsScreen.SettingsScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph
@NavGraph
annotation class SettingsNavGraph(
    val start: Boolean = false
)


@SettingsNavGraph(start = true)
@Destination
@Composable
fun SettingsDes(
    navigator: DestinationsNavigator,
    mainActivityViewModel: MainActivityViewModel,
    settingsScreenViewModel: SettingsScreenViewModel
) {
    SettingsScreen(
        navigateEditProfile = { navigator.navigate(EditProfileDesDestination) },
        navigateChangePassword = { navigator.navigate(ChangePasswordDesDestination) },
        mainActivityViewModel = mainActivityViewModel,
        settingsScreenViewModel = settingsScreenViewModel
    )
}

@SettingsNavGraph
@Destination
@Composable
fun EditProfileDes(
    navigator: DestinationsNavigator,
    viewModel: MainActivityViewModel,
    settingsScreenViewModel: SettingsScreenViewModel
) {
    EditProfile(
        navigateBackProfile = { navigator.navigateUp() },
        mainActivityViewModel = viewModel,
        settingsScreenViewModel = settingsScreenViewModel
    )
}

@SettingsNavGraph
@Destination
@Composable
fun ChangePasswordDes(
    navigator: DestinationsNavigator,
    viewModel: MainActivityViewModel,
    settingsScreenViewModel: SettingsScreenViewModel
) {
    ChangePassword(
        navigateUp = { navigator.navigateUp() },
        viewModel = viewModel,
        settingsScreenViewModel = settingsScreenViewModel
    )
}