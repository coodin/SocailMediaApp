package com.example.presentation.ui.screens.profileScreen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginsignupcompose.R
import com.example.presentation.ui.screens.components.VertiaclSpacer
import com.example.presentation.ui.theme.AppTheme
import com.example.utility.TAG

@Composable
fun SettingsScreen(navigateBacktoProfile: () -> Unit) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = { SettingsAppBar(navigateBacktoProfile) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            SettingProfile()
            VertiaclSpacer(space = AppTheme.dimens.grid_2)
            SettingsInput(
                labelText = "Name",
                inputValue = name,
                onValueChange = { newName -> name = newName }
            )
            VertiaclSpacer(space = AppTheme.dimens.grid_2)
            SettingsInput(
                labelText = "Your Email",
                inputValue = email,
                onValueChange = { newEmail -> email = newEmail }
            )
            VertiaclSpacer(space = AppTheme.dimens.grid_2)
            SettingsInput(
                labelText = "Password",
                inputValue = password,
                onValueChange = { newPassword ->
                    password = newPassword
                })
            VertiaclSpacer(space = 16.dp)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.profileHighLightColor,
                    contentColor = AppTheme.colors.onProfileButtonColor
                )
            ) {
                Text(text = "Save Now", style = AppTheme.typography.NetflixF20W500)
            }
        }
    }


}

@Composable
fun SettingProfile() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        val context = LocalContext.current
        var profilePicture by remember {
            mutableStateOf(R.drawable.profile_picture)
        }
        var isOpen by remember {
            mutableStateOf(false)
        }
        val onActivateAlert: (Boolean) -> Unit = { isOpen = it }
        LaunchedEffect(key1 = isOpen){

        }
        //DisposableEffect(key1 = , effect = )
        Permission(
            permission = Manifest.permission.CAMERA,
            permissionNotAvailableContent = {
                Column() {
                    Text(
                        "Camera permission required for this feature to be available. \" +\n" +
                                "                            \"Please grant the permission!"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        })
                    }) {
                        Text("Open Settings")
                    }
                }
            },
            onDrawableChange = { drawableID -> profilePicture = drawableID },
            isOpen = isOpen,
            onActivateAlert = onActivateAlert
        )
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable { onActivateAlert.invoke(true) },
            painter = painterResource(id = profilePicture),
            contentDescription = "Settings Profile Picture",
            contentScale = ContentScale.Fit
        )
        VertiaclSpacer(space = AppTheme.dimens.grid_1)
        Text(text = "Saba", style = AppTheme.typography.NetflixF26W500)
        VertiaclSpacer(space = AppTheme.dimens.grid_1)
        Text(text = "Edit profile", style = AppTheme.typography.NetflixF18W500)
    }
}

@Composable
fun SettingsInput(labelText: String, inputValue: String, onValueChange: (String) -> Unit) {
    Text(
        text = labelText,
        style = AppTheme.typography.NetflixF16W400,
        color = AppTheme.colors.profileSoftTextColor
    )
    VertiaclSpacer(space = AppTheme.dimens.grid_1)
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        //label = { Text(text = labelText) },
        //placeholder = { Text(text = "Name Field") },
        value = inputValue, onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AppTheme.colors.profileTextColor,
            cursorColor = AppTheme.colors.profileTextColor
        )
    )
}

@Composable
fun SettingsAppBar(navigateBackToProfile: () -> Unit) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentPadding = PaddingValues(horizontal = 16.dp),
        elevation = 0.dp
    ) {
        BackHandler() {
            Log.d(TAG, "The system back button has been tapped")
            navigateBackToProfile.invoke()
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navigateBackToProfile.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back to Profile",
                    tint = AppTheme.colors.profileHighLightColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Profile",
                style = AppTheme.typography.NetflixF20W500,
                color = AppTheme.colors.profileTextColor,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { /*TODO*/ },
                enabled = false
            ) {

            }

//            Box(
//                modifier = Modifier.size(24.dp)
//            ) {
//
//            }
        }

    }
}