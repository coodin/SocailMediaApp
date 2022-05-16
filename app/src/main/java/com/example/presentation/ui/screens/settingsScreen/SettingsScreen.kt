package com.example.presentation.ui.screens.settingsScreen

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.use_case.validation.ValidatePassword
import com.example.loginsignupcompose.R
import com.example.presentation.MainActivityViewModel
import com.example.presentation.ui.screens.Components.TemporaryScreen
import com.example.presentation.ui.screens.Components.VerticalSpacer
import com.example.presentation.ui.theme.AppTheme

@Composable
fun SettingsScreen(
    navigateEditProfile: () -> Unit,
    navigateChangePassword: () -> Unit,
    mainActivityViewModel: MainActivityViewModel,
    settingsScreenViewModel: SettingsScreenViewModel
) {
    val userPreferences = settingsScreenViewModel.userPreferences
    //val loading = mainActivityViewModel.isCurrent
    LaunchedEffect(key1 = userPreferences) {
        Log.d(
            "Settings",
            "email: ${userPreferences?.email} " +
                    "and isLight: ${userPreferences?.darkLightMode} " +
                    "and isNotification enabled ${userPreferences?.notification}"
        )
    }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.grid_2)
                .fillMaxSize()
                .padding(vertical = AppTheme.dimens.grid_1)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Settings",
                style = AppTheme.typography.NetflixF26W500.copy(fontSize = 36.sp),
                color = AppTheme.colors.editScreenTextColor
            )
            VerticalSpacer(space = AppTheme.dimens.grid_3)
            ItemsOfScreen(
                image = R.drawable.ic_dark_mode,
                text = "Dark Mode",
                navigateDifferentScreen = { },
                isArrow = false,
                switchValue = userPreferences?.darkLightMode,
                onSwitchChange = { value -> settingsScreenViewModel.changeDarkMode(value) }
            )
            VerticalSpacer(space = AppTheme.dimens.grid_3)
            Text(
                text = "Profile",
                style = AppTheme.typography.NetflixF18W500,
                color = AppTheme.colors.editScreenTitleTextColor
            )
            VerticalSpacer(space = AppTheme.dimens.grid_1)
            ItemsOfScreen(
                image = R.drawable.ic_edit_profile,
                text = "Edit Profile",
                navigateDifferentScreen = { navigateEditProfile.invoke() }
            )
            VerticalSpacer(space = AppTheme.dimens.grid_1)
            ItemsOfScreen(
                image = R.drawable.ic_edit_password,
                text = "Change Password",
                navigateDifferentScreen = { navigateChangePassword.invoke() }
            )
            VerticalSpacer(space = AppTheme.dimens.grid_3)
            Text(
                text = "Notifications",
                style = AppTheme.typography.NetflixF18W500,
                color = AppTheme.colors.editScreenTitleTextColor
            )
            VerticalSpacer(space = AppTheme.dimens.grid_1)
            ItemsOfScreen(
                image = R.drawable.ic_notifications,
                text = "Notifications",
                navigateDifferentScreen = { },
                isArrow = false,
                switchValue = userPreferences?.notification,
                onSwitchChange = { value -> settingsScreenViewModel.changeNotification(value) }
            )
            VerticalSpacer(space = AppTheme.dimens.grid_3)
            Text(
                text = "Regional",
                style = AppTheme.typography.NetflixF18W500,
                color = AppTheme.colors.editScreenTitleTextColor
            )
            VerticalSpacer(space = AppTheme.dimens.grid_1)
            ItemsOfScreen(
                image = R.drawable.ic_languages,
                text = "Language",
                navigateDifferentScreen = { }
            )
            VerticalSpacer(space = AppTheme.dimens.grid_1)
            ItemsOfScreen(
                image = R.drawable.ic_logout,
                text = "Logout",
                navigateDifferentScreen = { mainActivityViewModel.signOut() }
            )
            VerticalSpacer(space = AppTheme.dimens.grid_2)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "App ver 2.0.1",
                    style = AppTheme.typography.NetflixF16W500,
                    color = AppTheme.colors.editScreenTitleTextColor,
                )
            }
        }
    }
}

@Composable
fun ItemsOfScreen(
    @DrawableRes image: Int,
    text: String, navigateDifferentScreen: () -> Unit,
    isArrow: Boolean = true,
    switchValue: Boolean? = null,
    onSwitchChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimens.grid_1, horizontal = AppTheme.dimens.grid_1),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = image), contentDescription = "")
        Spacer(modifier = Modifier.weight(0.4f))
        Text(
            text = text,
            color = AppTheme.colors.editScreenTextColor,
            style = AppTheme.typography.NetflixF16W500
        )
        Spacer(modifier = Modifier.weight(0.6f))
        if (isArrow) {
            IconButton(onClick = { navigateDifferentScreen.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more), contentDescription = "",
                    tint = AppTheme.colors.editScreenTextColor
                )
            }
        } else {
            Switch(
                checked = switchValue ?: false,
                onCheckedChange = onSwitchChange,
                // colors = SwitchDefaults.colors()
            )
        }

    }
}