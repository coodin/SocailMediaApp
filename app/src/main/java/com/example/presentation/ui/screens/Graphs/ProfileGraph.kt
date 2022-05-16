package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.MainActivityViewModel
import com.example.presentation.ui.screens.Graphs.destinations.ChangePasswordDesDestination
import com.example.presentation.ui.screens.Graphs.destinations.EditProfileDesDestination
import com.example.presentation.ui.screens.Graphs.destinations.SettingsDesDestination
import com.example.presentation.ui.screens.ProfileScreen.ProfileScreen
import com.example.presentation.ui.screens.ProfileScreen.ProfileViewModel
import com.example.presentation.ui.screens.settingsScreen.SettingsScreen
import com.example.presentation.ui.screens.settingsScreen.SettingsScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@BottomNavGraph
@NavGraph
annotation class ProfileNavGraph(
    val start: Boolean = false
)

@ProfileNavGraph(start = true)
@Destination
@Composable
fun ProfileDes(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel(),
    mainActivityViewModel: MainActivityViewModel
) {
    ProfileScreen(
        viewModel = viewModel,
        navigateProfileComposable = { navigator.navigate(SettingsDesDestination) },
        mainActivityViewModel = mainActivityViewModel
    )
}

//@ProfileNavGraph
//@Destination
//@Composable
//fun SettingsDes(
//    navigator: DestinationsNavigator,
//    mainActivityViewModel: MainActivityViewModel,
//) {
//    SettingsScreen(
//        navigateEditProfile = { navigator.navigate(EditProfileDesDestination) },
//        navigateChangePassword = { navigator.navigate(ChangePasswordDesDestination) },
//        mainActivityViewModel = mainActivityViewModel
//    )
//}


//@Destination
//@Composable
//fun EditProfileDes(navigator: DestinationsNavigator, viewModel: MainActivityViewModel) {
//    EditProfile(navigateBackProfile = { navigator.navigateUp() }, viewModel)
//}
//
//@Destination
//@Composable
//fun ChangePasswordDes(navigator: DestinationsNavigator, viewModel: MainActivityViewModel) {
//    ChangePassword(navigateUp = { navigator.navigateUp() }, viewModel = viewModel)
//}