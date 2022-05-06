package com.example.presentation.ui.screens.Graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation.ui.screens.Graphs.destinations.HomeDesDestination
import com.example.presentation.ui.screens.Graphs.destinations.SignUpDesDestination
import com.example.presentation.ui.screens.LoginScreen.Login
import com.example.presentation.ui.screens.LoginScreen.LoginViewModel
import com.example.presentation.ui.screens.SignupScreen.SignUpScreen
import com.example.presentation.ui.screens.SignupScreen.SignUpViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.utils.startDestination


@RootNavGraph
@NavGraph
annotation class LoginSignUpNavGraph(
    val start: Boolean = false
)

@LoginSignUpNavGraph(start = true)
@Destination
@Composable
fun LoginDes(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Login(
        viewModel = viewModel,
        navigateMain = {
            navigator.navigate(NavGraphs.bottom) {
                navigator.popBackStack()
            }
        },
        navigateSignUp = { navigator.navigate(SignUpDesDestination) }
    )
}


@LoginSignUpNavGraph
@Destination
@Composable
fun SignUpDes(
    navigator: DestinationsNavigator,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    SignUpScreen(viewModel = viewModel, navigateMain = { id ->
        navigator.navigate(NavGraphs.bottom) {
            navigator.popBackStack()
        }
    })
}