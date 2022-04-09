package com.example.presentation.ui.screens.signupScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.loginsignupcompose.R
import com.example.presentation.ui.screens.components.AutoSizeText
import com.example.presentation.ui.screens.components.GoogleOrFacebook
import com.example.presentation.ui.screens.components.TemporaryScreen
import com.example.presentation.ui.screens.components.VertiaclSpacer
import com.example.presentation.ui.theme.AppTheme
import com.example.presentation.ui.theme.colorOfFacebook
import com.example.utility.State
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(viewModel: SignUpViewModel, navigateMain: (String?) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    //val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val loadingState = viewModel.loadingState


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
        val scrollState = rememberScrollState()
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(
                        top = 40.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 10.dp
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bubble_gum_body_with_phone),
                    contentDescription = "Welcome Illustration",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.height(234.dp)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    VertiaclSpacer(space = AppTheme.dimens.grid_2)
                    AutoSizeText(text = "Sign up takes only", textStyle = MaterialTheme.typography.h3)
                    AutoSizeText(text = "2 minutes", textStyle = MaterialTheme.typography.h3)
//                    Text(
//                        text = "Sign up takes only 2 minutes",
//                        style = TextStyle(
//                            fontSize = 40.sp, fontWeight =
//                            FontWeight.Bold,
//                            textAlign = TextAlign.Center
//                        )
//                    )
                    VertiaclSpacer(space = AppTheme.dimens.grid_2)
                    GoogleOrFacebook(
                        text = "Sign Up With Google",
                        icon = painterResource(id = R.drawable.icons8_google)
                    )
                    VertiaclSpacer(space = AppTheme.dimens.grid_2)
                    GoogleOrFacebook(
                        text = "Sign Up With Facebook",
                        icon = painterResource(id = R.drawable.ic_icons8_facebook),
                        contentColor = AppTheme.colors.colorOfFacebook,
                        backgroundColor = Color.White
                    )
                    VertiaclSpacer(space = AppTheme.dimens.grid_3)
                    OutlinedTextField(
                        value = email,
                        onValueChange = { newValue -> email = newValue },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Email") })
                    VertiaclSpacer(space = AppTheme.dimens.grid_1)
                    OutlinedTextField(
                        value = password,
                        onValueChange = { newValue -> password = newValue },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Password") },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    VertiaclSpacer(space = AppTheme.dimens.grid_2)
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.signUp(email.trim(), password.trim())
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
                        Text(text = "Sign up")
                    }
                    VertiaclSpacer(space = AppTheme.dimens.grid_3)
                }
            }
            TemporaryScreen(loading)
        }

    }
}