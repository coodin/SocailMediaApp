package com.example.presentation.ui.screens.navHost


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.AppBottomNavigation
import com.example.presentation.HomeViewModel
import com.example.presentation.ui.screens.splashScreen.AnimatedSplashScreen
import com.example.utility.Greeting

@Composable
fun MyNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.LoginSingUpRoutes.route,
    ) {
        composable(NavigationRoutes.Main.route) { backStackEntry ->
            val userID = backStackEntry.arguments?.getString("userID")
            AppBottomNavigation(userID!!)
        }
        composable(NAV_SPLASH) { AnimatedSplashScreen(navController) }
        loginSignUpScreen(navController)
    }
}


@Composable
fun Home(viewModel: HomeViewModel, navigateLogin: () -> Unit) {
    Greeting(viewModel = viewModel, navigateLogin = navigateLogin)
}

@Composable
fun Feed(navigateFeedComposable: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Feed")
        Button(onClick = { navigateFeedComposable() }) {
            Text(text = "Navigate to FeedComposable")
        }
    }
}
