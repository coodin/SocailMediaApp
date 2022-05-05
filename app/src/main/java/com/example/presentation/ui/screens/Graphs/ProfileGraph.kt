package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.ui.screens.ProfileScreen.ProfileScreen
import com.example.presentation.ui.screens.ProfileScreen.ProfileViewModel
import com.example.presentation.ui.screens.ProfileScreen.SettingsScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph


@BottomNavGraph
@NavGraph
annotation class ProfileNavGraph(
    val start: Boolean = false
)

@ProfileNavGraph(start = true)
@Destination
@Composable
fun ProfileDes(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    ProfileScreen(viewModel = viewModel, navigateProfileComposable = {})
}

@ProfileNavGraph
@Destination
@Composable
fun SettingsDes(navController: NavController) {
    SettingsScreen(navigateBackProfile = { navController.navigateUp() })
}