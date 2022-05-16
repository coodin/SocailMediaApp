package com.example.presentation.ui.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.example.presentation.MainActivityViewModel
import com.example.utility.State
import kotlinx.coroutines.launch

@Composable
fun Home(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: HomeViewModel,
    navigateSecondScreen: () -> Unit,
    mainActivityViewModel: MainActivityViewModel
) {
    // Creates a CoroutineScope bound to the MoviesScreen's lifecycle
    val scope = rememberCoroutineScope()
    //val scrollState = rememberScrollState()

    // If `lifecycleOwner` changes, dispose and reset the effect
//    DisposableEffect(lifecycleOwner) {
//        // Create an observer that triggers our remembered callbacks
//        // for sending analytics events
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_STOP) {
//                activityViewModel.onStop()
//            } else if (event == Lifecycle.Event.ON_START) {
//                activityViewModel.onStart()
//            }
//        }
//
//        // Add the observer to the lifecycle
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        // When the effect leaves the Composition, remove the observer
//        onDispose {
//            Log.d(TAG, "Effect left from the  composition ")
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            // Drawer items
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    navigateSecondScreen()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription =
                    "Favorite Icon"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
            ) {
                when (val dataSet = mainActivityViewModel.userState) {
                    is State.Failed -> {
                        Text(
                            dataSet.message,
                        )
                    }
                    is State.Success -> {
                        val dataset = dataSet.data
                        LazyColumn(
                            modifier = Modifier
                                .wrapContentWidth()
                                .background(Color.DarkGray)
                        ) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    CompositionLocalProvider(
                                        LocalContentColor provides Color.White,
                                        LocalTextStyle provides MaterialTheme.typography.body1
                                    ) {
                                        Text(dataset?.email ?: "")
                                        //Text(it?.country ?: "")
                                        //Text("${it?.population}")
                                    }
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(MaterialTheme.colors.background)
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {  },
                        colors = ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(text = "Sign Out")
                    }
                    Button(
                        onClick = { /*activityViewModel.updateCity()*/ },
                        colors = ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(text = "Update City")
                    }
                    Button(
                        onClick = { /*activityViewModel.updateTimeStamp() */ }, colors = ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(
                            text = "Update TimeStamp",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { /*activityViewModel.deleteDocument()*/ }, colors = ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(
                            text = "Delete Document",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Button(
                        onClick = {
                            // activityViewModel.getDocument()
                        }, colors =
                        ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(
                            text = "Get Document",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    when (val documentState = viewModel.documentState) {
                        is State.Success -> {
                            Text(text = documentState.data?.name ?: "")
                            Text(text = documentState.data?.country ?: "")
                            Text(text = documentState.data?.population.toString())
                        }
                        is State.Loading -> {
                            CircularProgressIndicator()
                        }
                        is State.Failed -> {
                            Text(documentState.message)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}