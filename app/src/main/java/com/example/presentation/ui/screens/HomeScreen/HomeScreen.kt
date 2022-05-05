package com.example.presentation.ui.screens.HomeScreen

import androidx.compose.runtime.Composable
import com.example.utility.Greeting

@Composable
fun Home(
    viewModel: HomeViewModel,
    navigateSecondScreen: () -> Unit,
) {
    Greeting(viewModel = viewModel, navigateSecondScreen = navigateSecondScreen)
}