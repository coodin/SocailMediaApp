package com.example.presentation


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.presentation.ui.screens.FavortieScreen.FavoriteViewModel
import com.example.presentation.ui.screens.FeedScreen.FeedViewModel
import com.example.presentation.ui.screens.Graphs.BottomBarDestination
import com.example.presentation.ui.screens.Graphs.NavGraphs
import com.example.presentation.ui.screens.Graphs.appCurrentDestinationAsState
import com.example.presentation.ui.screens.Graphs.destinations.Destination
import com.example.presentation.ui.screens.HomeScreen.HomeViewModel
import com.example.presentation.ui.screens.ProfileScreen.ProfileViewModel
import com.example.presentation.ui.theme.LoginSignUpComposeTheme
import com.example.utility.TAG
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.contains
import com.ramcosta.composedestinations.utils.startDestination
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // FirebaseApp.initializeApp(this)
        setContent {
            LoginSignUpComposeTheme {
                // MyNavHost()
                // MainScreen()
                val navController = rememberNavController()
                val currentDestination: Destination? = navController.appCurrentDestinationAsState().value
                Scaffold(
                    bottomBar = {
                        BottomBarDestination.values().forEach { destination ->
                            currentDestination?.let { destination.graph.contains(it) }?.let { value ->
                                if (value) {
                                    BottomBar(navController)
                                }
                            }
                        }
                    }
                ) {

                    DestinationsNavHost(
                        modifier = Modifier.padding(it),
                        navGraph = NavGraphs.root,
                        navController = navController, //!! this is important
                        dependenciesContainerBuilder = {
                            // To tie SettingsViewModel to "settings" nested navigation graph,
                            // making it available to all screens that belong to it
//                            when {
//                                NavGraphs.home.contains(destination) -> {
//                                    val parentEntry = remember {
//                                        navController.getBackStackEntry(NavGraphs.home.route)
//                                    }
//                                    dependency(hiltViewModel<HomeViewModel>(parentEntry))
//                                }
//                                NavGraphs.favorite.contains(destination) -> {
//                                    val parentEntry = remember {
//                                        navController.getBackStackEntry(NavGraphs.favorite.route)
//                                    }
//                                    dependency(hiltViewModel<FavoriteViewModel>(parentEntry))
//                                }
//                                NavGraphs.feed.contains(destination) -> {
//                                    val parentEntry = remember {
//                                        navController.getBackStackEntry(NavGraphs.feed.route)
//                                    }
//                                    dependency(hiltViewModel<FeedViewModel>(parentEntry))
//                                }
//                                NavGraphs.profile.contains(destination) -> {
//                                    val parentEntry = remember {
//                                        navController.getBackStackEntry(NavGraphs.profile.route)
//                                    }
//                                    dependency(hiltViewModel<ProfileViewModel>(parentEntry))
//                                }
//                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val currentDestination: Destination? = navController.appCurrentDestinationAsState().value


    //Log.d(TAG, "First Graph: ${navController.graph.findStartDestination()}")
    navController.backQueue.forEach {
        Log.d(TAG, "BackStack: ${it.destination.route}")
    }
    Log.d(TAG,"\n")
    //val active = NavGraphs.home.contains()
    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            currentDestination?.let { destination.graph.contains(it) }?.let {
                BottomNavigationItem(
                    selected = it,
                    onClick = {
                        Log.d(TAG,"Graph route = ${destination.graph.route}")
//                        Log.d(TAG, " Graph route : ${destination.graph.route}")
//                        Log.d(TAG, " Graph start route : ${destination.graph.startRoute.route}")
                        navController.navigateTo(destination.graph) {
                            launchSingleTop = true
                            popUpTo(NavGraphs.bottom) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    },
                    icon = { Icon(destination.icon, contentDescription = "") },
                    //label = { Text(stringResource(destination.label)) },
                )
            }
        }
    }
}
//
////@RootNavGraph(start = true)
//@SettingsNavGraph(start = true)
//@Destination()
//@Composable
//fun ProfileScreen(
//    navigator: DestinationsNavigator,
//) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column() {
//            Text(text = "Hello from Profile Screen")
//            Button(onClick = {
//                navigator.navigate(
//                    LolDestination(ProfileScreenNavArgs(id = 5, groupName = "ogün"))
//                )
//            }) {
//                Text(text = "navigate second screen")
//            }
//        }
//    }
//}
//
//
//data class ProfileScreenNavArgs(
//    val id: Long,
//    val groupName: String?
//)
//
//@SettingsNavGraph
//@Destination(navArgsDelegate = ProfileScreenNavArgs::class)
//@Composable
//fun Lol(
//    navigator: DestinationsNavigator,
//    viewModel: MyScreenViewModel = hiltViewModel()
//) {
//    //val navigator = viewModel.data.navigator
//    LaunchedEffect(key1 = Unit) {
//        Log.d(TAG, "ID : ${viewModel.data.id}")
//        Log.d(TAG, "Group Name : ${viewModel.data.groupName}")
//    }
//    //val viewModel = hiltViewModel<MyScreenViewModel>()
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column() {
//            Button(
//                onClick = {
//                    navigator.navigate(SettingsMainDestination)
//                }) {
//                Text(text = "navigate to Settings screen")
//            }
//            Text(text = "Hello from Lol Screen")
//        }
//    }
//}
//
//@HiltViewModel
//class MyScreenViewModel @Inject constructor(
//    private val savedStateHandle: SavedStateHandle,
//) : ViewModel() {
//    val data = LolDestination.argsFrom(savedStateHandle)
//
//    init {
//        Log.d(TAG, "MyScreenViewModel has been instantiated")
////        Log.d(TAG, "${data.groupName}")
////        Log.d(TAG, "${data.id}")
////        Log.d(TAG, "id of the data :${data.id}")
////        Log.d(TAG, "groupName of the data :${data.groupName}")
//    }
//}
//
//@RootNavGraph(start = true)
//@NavGraph
//annotation class SettingsNavGraph(
//    val start: Boolean = false
//)
//
//@SettingsNavGraph
//@Destination
//@Composable
//fun SettingsMain(
//    navigator: DestinationsNavigator
//) {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Column() {
//            Text(text = "Settings Screen")
//            Button(onClick = { navigator.navigate(ProfileScreenDestination) }) {
//                Text(text = "Go ot main Screen")
//            }
//        }
//    }
//}
//
//enum class BottomBarDestination(
//    val graph: NavGraphSpec,
//    val icon: ImageVector,
//    @StringRes val label: Int
//)



