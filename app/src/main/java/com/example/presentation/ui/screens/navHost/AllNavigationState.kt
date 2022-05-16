package com.example.presentation.ui.screens.navHost


import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Send
import androidx.compose.ui.graphics.vector.ImageVector

import com.example.loginsignupcompose.R

sealed class NavigationRoutes(
    val route: String,
    @StringRes val resourceId: Int? = null,
    val icon: ImageVector? = null,
    val graphRoute: String? = null
) {
    object SingUp : NavigationRoutes(route = NAV_SINGUP) {
        fun createRoute(): String {
            return "Signup"
        }
    }

    object Login : NavigationRoutes(route = NAV_LOGIN) {
        fun createRoute(): String {
            return "Login"
        }
    }

    object Main : NavigationRoutes(route = NAV_MAIN) {
        fun createRoute(): String {
            return "Main"
        }
    }

    object ProfileSettings : NavigationRoutes(route = NAV_PROFILE_SETTINGS) {
        fun createRoute(): String {
            return "ProfileSettings"
        }
    }

    object ChatScreen : NavigationRoutes(route = NAV_CHAT_SCREEN) {
        fun createRoute(): String {
            return "ChatScreen"
        }
    }


    // Nav Graph  Routes

    object HomeRoute : NavigationRoutes(route = HOME_ROUTE) {
        fun createRoute(uid: String): String {
            return "HomeRoute/${uid}"
        }
    }

    object FavRoute : NavigationRoutes(route = FAVORITE_ROUTE) {
        fun createRoute(): String {
            return "FavoriteRoute"
        }
    }

    object FeedRoute : NavigationRoutes(route = FEED_ROUTE) {
        fun createRoute(): String {
            return "FeedRoute"
        }
    }


    object ProfileRoute : NavigationRoutes(route = PROFILE_ROUTE) {
        fun createRoute(uid: String): String {
            return "ProfileRoute"
        }
    }

    object LoginSingUpRoutes : NavigationRoutes(route = LOGIN_SIGNUP_ROUTE) {
        fun createRoute(): String {
            return "LoginSignUpRoute"
        }
    }

    // Bottom Navigation Routes
    object Home : NavigationRoutes(
        route = NAV_HOME,
        resourceId = R.string.home,
        icon = Icons.Outlined.Home,
        graphRoute = HOME_ROUTE
    ) {
        fun createRoute(): String {
//            return NAV_HOME.substringBefore("{").plus(uid)
            return "Home"
        }

        fun createGraphRouth(uid: String): String {
            return "HomeRoute/${uid}"
        }
    }

    object Fav : NavigationRoutes(
        route = NAV_FAV,
        resourceId = R.string.fav,
        icon = Icons.Outlined.Send,
        graphRoute = FAVORITE_ROUTE
    ) {
        fun createRoute(uid: String? = null): String {
            return "Fav"
        }
    }

    object Feed : NavigationRoutes(
        route = NAV_FEED,
        resourceId = R.string.feed,
        icon = Icons.Outlined.FavoriteBorder,
        graphRoute = FEED_ROUTE
    ) {
        fun createRoute(): String {
            return "Feed"
        }
    }

    object Profile : NavigationRoutes(
        route = NAV_PROFILE,
        resourceId = R.string.profile,
        icon = Icons.Outlined.PersonOutline,
        graphRoute = PROFILE_ROUTE
    ) {
        fun createRoute(): String {
            return "Profile"
        }
    }
}