package com.example.presentation.ui.screens.navHost

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.presentation.ui.screens.loginScreen.Login
import com.example.presentation.ui.screens.loginScreen.LoginViewModel
import com.example.presentation.ui.screens.signupScreen.SignUpScreen
import com.example.presentation.ui.screens.signupScreen.SignUpViewModel

fun NavGraphBuilder.loginSignUpScreen(navController: NavController) {
    navigation(startDestination = NavigationRoutes.Login.route, route = NavigationRoutes.LoginSingUpRoutes.route) {
        composable(
            route = NavigationRoutes.Login.route,
        ) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            Login(loginViewModel,
                navigateMain = { uid ->
                    navController.navigate(NavigationRoutes.Main.createRoute(uid!!)) {
                        popUpTo(NavigationRoutes.Login.createRoute()) {
                            inclusive = true
                        }
                    }
                },
                navigateSignUp = {
                    navController.navigate(NAV_SINGUP)
                }
            )
        }
        composable(route = NavigationRoutes.SingUp.route) {
            val signUpViewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(signUpViewModel, navigateMain = { uid ->
                /*
                   This code is same with below and its responsibility is remove back stack up to specified destination.
                navController.popBackStack(route = NAV_LOGIN,inclusive = true)
                 */
                navController.navigate(NavigationRoutes.Main.createRoute(uid!!)) {
                    popUpTo(NavigationRoutes.Login.createRoute()) {
                        inclusive = true
                    }
                }
            })
        }
    }
}