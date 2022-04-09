package com.example.presentation.ui.screens.loginScreen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.loginsignupcompose.R
import com.example.presentation.ui.screens.components.*
import com.example.presentation.ui.theme.AppTheme
import com.example.presentation.ui.theme.colorOfFacebook
import com.example.utility.State
import com.google.accompanist.permissions.*
import kotlinx.coroutines.launch


@Composable
fun Login(
    viewModel: LoginViewModel,
    navigateMain: (uid: String?) -> Unit,
    navigateSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val loadingState = viewModel.loadingState

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 16.sp)) {
            append("Don't have an account? ")
        }
        withStyle(style = SpanStyle(color = Color.Blue, fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
            append("Sign up here")
        }
    }
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts
//            .StartActivityForResult()
//    ) {
//
//    }
//
//    BoxWithConstraints {
//
//    }

    LaunchedEffect(key1 = loadingState) {
        when (loadingState) {
            is State.Success -> {
                navigateMain(loadingState.data?.uid)
            }
            is State.Failed -> {
                snackbarHostState.showSnackbar(
                    actionLabel = "Dismiss",
                    message = loadingState.message,
                    duration = SnackbarDuration.Long
                )
            }
            else -> {}
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        val loading = loadingState is State.Loading
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Transparent
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            Color("#2c3e50".toColorInt()),
//                            Color("#bdc3c7".toColorInt())
//                        )
//                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(
                        top = 40.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 10.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(234.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bubble_gum_social_media),
                        contentDescription = "Login Illustration",
                        contentScale = ContentScale.FillWidth,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AutoSizeText(
                        text = "Welcome back",
                        MaterialTheme.typography.h3
                    )
                    VertiaclSpacer(space = AppTheme.dimens.grid_1)
                    GoogleOrFacebook(
                        text = "Sign In With Google",
                        icon = painterResource(id = R.drawable.icons8_google)
                    )
                    VertiaclSpacer(space = AppTheme.dimens.grid_2)
                    GoogleOrFacebook(
                        text = "Sign In With Facebook",
                        icon = painterResource(id = R.drawable.ic_icons8_facebook),
                        contentColor = AppTheme.colors.colorOfFacebook,
                        backgroundColor = Color.White
                    )
                    VertiaclSpacer(space = AppTheme.dimens.grid_1)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .weight(1f), color = Color.Gray
                        )
                        HorizantalSpacer(space = AppTheme.dimens.grid_2)
                        Text(text = "or", color = Color.LightGray)
                        HorizantalSpacer(space = AppTheme.dimens.grid_2)
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .weight(1f), color = Color.Gray
                        )

                    }
                    VertiaclSpacer(space = AppTheme.dimens.grid_1)
                    OutlinedTextField(
                        value = email,
                        label = { Text(text = "Email") },
                        onValueChange = { newValue -> email = newValue },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Email") })
                    VertiaclSpacer(space = AppTheme.dimens.grid_1)
                    OutlinedTextField(
                        value = password,
                        onValueChange = { newValue ->
                            password = newValue
                        },
                        label = { Text(text = "Password") },
                        visualTransformation = if (passwordVisibility)
                            VisualTransformation.None else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Password") },
                        trailingIcon = {
                            val image = if (passwordVisibility)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Toggle Password"
                                )
                            }
                        }
                    )
                    VertiaclSpacer(space = AppTheme.dimens.grid_2)
                    Button(
                        onClick = {
                            coroutineScope.launch {
                               // viewModel.signIn(email.trim(), password.trim())
                                navigateMain("123")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppTheme.colors.profileHighLightColor,
                            contentColor = AppTheme.colors.onProfileButtonColor
                        ),
                        shape = RoundedCornerShape(20)
                    ) {
                        Text(text = "Login")
                    }
                    VertiaclSpacer(space = AppTheme.dimens.grid_2)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
//                            Text("Don't have an account? ", fontSize = 16.sp)
//                            Text(text = "Sign up here", color = Color.Blue, fontSize = 16.sp)
                        ClickableText(
                            text = annotatedString,
                            onClick = { offset ->
                                if (offset >= 24) {
                                    navigateSignUp()
                                }
                            }
                        )
                    }
                    VertiaclSpacer(space = AppTheme.dimens.grid_1)
                }
            }
            TemporaryScreen(loading)
        }
    }


}


@Composable
fun GetContentExample() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    val painter = rememberAsyncImagePainter(imageUri)


    Column {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Load Image")
        }
        Image(
            painter = painter,
            contentDescription = "My Image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeatureThatRequiresCameraPermission() {

    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )
    when (cameraPermissionState.status) {
        // If the camera permission is granted, then show screen with the feature enabled
        PermissionStatus.Granted -> {
            Text("Camera permission Granted")
        }
        is PermissionStatus.Denied -> {
            Column {
                val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    "The camera is important for this app. Please grant the permission."
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    "Camera permission required for this feature to be available. " +
                            "Please grant the permission"
                }

                Text(textToShow)
                Button(onClick = {
                    cameraPermissionState.launchPermissionRequest()
                }) {
                    Text("Request permission")
                }
            }
        }
    }
}

@Composable
fun BackButton(function: () -> Int) {

    var backPressedCount by remember { mutableStateOf(0) }
    BackHandler { backPressedCount++ }

    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    Button(onClick = { dispatcher.onBackPressed() }) {
        Text("Press Back count $backPressedCount")
    }
}