package com.example.presentation.ui.screens.navHost
//
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.navigation
//import com.example.presentation.ui.screens.LoginScreen.Login
//import com.example.presentation.ui.screens.LoginScreen.LoginViewModel
//import com.example.presentation.ui.screens.SignupScreen.SignUpScreen
//import com.example.presentation.ui.screens.SignupScreen.SignUpViewModel
//
//fun NavGraphBuilder.loginSignUpScreen(navController: NavController) {
//    navigation(
//        startDestination = NavigationRoutes.Login.route,
//        route = NavigationRoutes.LoginSingUpRoutes.route
//    ) {
//        composable(
//            route = NavigationRoutes.Login.route,
//        ) {
//            val loginViewModel = hiltViewModel<LoginViewModel>()
//            Login(
//                loginViewModel,
//                navigateMain = {
//                    navController.navigate(NavigationRoutes.Main.createRoute()) {
//                        popUpTo(NavigationRoutes.LoginSingUpRoutes.route) {
//                            inclusive = true
//                        }
//                    }
//                },
//                navigateSignUp = {
//                    navController.navigate(NavigationRoutes.SingUp.createRoute())
//                }
//            )
//        }
//        composable(route = NavigationRoutes.SingUp.route) {
//            val signUpViewModel = hiltViewModel<SignUpViewModel>()
//            SignUpScreen(signUpViewModel, navigateMain = {
//                /*
//                   This code is same with below and its responsibility is remove back stack up to specified destination.
//                navController.popBackStack(route = NAV_LOGIN,inclusive = true)
//                 */
//                navController.navigate(NavigationRoutes.Main.createRoute()) {
//                    popUpTo(NavigationRoutes.LoginSingUpRoutes.route) {
//                        inclusive = true
//                    }
//                }
//            })
//        }
//    }
//}