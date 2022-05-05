package com.example.presentation.ui.screens.navHost


import androidx.navigation.*
import androidx.navigation.compose.*


//@Composable
//fun MyNavHost() {
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = NavigationRoutes.LoginSingUpRoutes.route,
//    ) {
//        composable(NavigationRoutes.Main.route) { backStackEntry ->
//            //val userID = backStackEntry.arguments?.getString("userID")
//            val mainViewModel = hiltViewModel<MainViewModel>()
//            //MainScreen()
//        }
//        composable(NAV_SPLASH) { AnimatedSplashScreen(navController) }
//        //loginSignUpScreen(navController)
//    }
//}

fun NavGraphBuilder.maincontent(navController: NavController) {
    navigation(
        startDestination = NavigationRoutes.HomeRoute.route,
        arguments = listOf(navArgument("ID") {
            type = NavType.StringType
            defaultValue = "NoUser"
        }),
        route = NavigationRoutes.Main.route
    ) {
        home(navController)
        favorite(navController)
        feed(navController)
        profile(navController)
    }
}
