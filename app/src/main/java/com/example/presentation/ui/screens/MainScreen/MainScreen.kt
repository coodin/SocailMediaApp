package com.example.presentation.ui.screens.MainScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.domain.model.UserProfile
//import com.example.presentation.BottomNavItem
import com.example.presentation.ui.screens.navHost.*
import com.example.presentation.ui.theme.AppTheme
import com.example.utility.COLOR_NORMAL
import com.example.utility.ICON_SIZE
import com.example.utility.TAG
import com.example.utility.State


@Composable
fun MainScreen(
//    navController: NavController,
//    currentDestination: NavDestination?,
//    itemBottoms: List<NavigationRoutes>,
    // mainViewModel: MainViewModel
) {
    var isBottomAppBarVisible by remember {
        mutableStateOf(true)
    }

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    // State of bottomBar, set state to false, if current page route is "car_details"
    val items = listOf(
        NavigationRoutes.Home,
        NavigationRoutes.Fav,
        NavigationRoutes.Feed,
        NavigationRoutes.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    isBottomAppBarVisible = when (currentDestination?.route) {
        NavigationRoutes.ProfileSettings.route -> false
        NavigationRoutes.ChatScreen.route -> false
        NavigationRoutes.Login.route -> false
        NavigationRoutes.SingUp.route -> false
        else -> true
    }

    for (destination in navController.backQueue) {
        Log.d(TAG, destination.destination.route.toString())
    }
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar =
        {
            AnimatedVisibility(
                visible = isBottomAppBarVisible,
                enter = slideInVertically(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    initialOffsetY = { it }
                ),
                exit = slideOutVertically(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    targetOffsetY = { it }
                ),
            ) {
                BottomAppBar(
                    backgroundColor = AppTheme.colors.profileHighLightColor,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(ICON_SIZE.times(1.5f))
                    ) {
                        items.forEach { screen ->
                            BottomNavItem(
                                //selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                selected = currentDestination?.route == screen.route,
                                icon = screen.icon!!,
                                onSelected = {
                                    navController.navigate(screen.graphRoute!!) {
                                        // Pop up to the start
                                        // destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        // NAV_HOME
                                        Log.d(
                                            TAG,
                                            "Grap start destination : ${navController.graph.findStartDestination().route}"
                                        )
                                        popUpTo(NavigationRoutes.Main.route) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reSelecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }

            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.LoginSingUpRoutes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            maincontent(navController)
            loginSignUpScreen(navController)
        }
    }
}


@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = ICON_SIZE,
    onSelected: () -> Unit,
    selected: Boolean
) {
//    var selectedIndex by remember {
//        mutableStateOf(0)
//    }
    AnimateIcon(
        imageVector = icon,
        scale = if (selected) 1.5f else 1.0f,
        color = if (selected) COLOR_NORMAL else COLOR_NORMAL.copy(alpha = 0.5f),
        iconSize = iconSize,
        onClick = onSelected
    )
}

@Composable
fun AnimateIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    iconSize: Dp = ICON_SIZE,
    scale: Float = 1f,
    color: Color = COLOR_NORMAL,
    onClick: () -> Unit
) {
    // Animation params
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        // Here the animation spec serves no purpose but to demonstrate in slow speed.
        animationSpec = TweenSpec(
            durationMillis = 100,
            easing = LinearEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 100,
            easing = LinearEasing
        )
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.size(iconSize)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "dummy",
            tint = animatedColor,
            modifier = modifier.scale(animatedScale)
        )
    }
}