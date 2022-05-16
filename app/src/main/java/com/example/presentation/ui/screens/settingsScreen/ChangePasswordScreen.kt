package com.example.presentation.ui.screens.ProfileScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.UserProfile
import com.example.presentation.MainActivityViewModel
import com.example.presentation.model.PasswordChangeEvent
import com.example.presentation.ui.screens.Components.VerticalSpacer
import com.example.presentation.ui.screens.settingsScreen.SettingsScreenViewModel
import com.example.presentation.ui.theme.AppTheme
import com.example.utility.State
import kotlinx.coroutines.flow.collect

@Composable
fun ChangePassword(
    navigateUp: () -> Unit,
    viewModel: MainActivityViewModel,
    settingsScreenViewModel: SettingsScreenViewModel
) {

    val state = settingsScreenViewModel.state
    //val context = LocalContext.current

//    LaunchedEffect(key1 = settingsScreenViewModel.validationEventChannel) {
//        //settingsScreenViewModel.validationEvents.collect { event ->
//        when (settingsScreenViewModel.validationEventChannel) {
//            is SettingsScreenViewModel.ValidationEvent.Success -> {
//                settingsScreenViewModel.changeDefault()
//            }
//            else -> {}
//            //  }
//        }
//    }
    Scaffold(
        topBar = {
            MyAppBar(navigateBackToProfile = { navigateUp.invoke() }, text = "Change Password")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .then(Modifier.padding(paddingValues))
                .padding(horizontal = AppTheme.dimens.grid_2)
                .fillMaxSize()
        ) {
            VerticalSpacer(space = AppTheme.dimens.grid_2)
            PasswordInput(
                labelText = "Current Password",
                isError = state.currentPasswordError != null,
                inputValue = state.currentPassword,
                onValueChange = { value ->
                    settingsScreenViewModel.onEvent(
                        PasswordChangeEvent.PasswordChanged(
                            value
                        )
                    )
                }
            )
            if (state.currentPasswordError != null) {
                Text(
                    text = state.currentPasswordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End),
                    style = AppTheme.typography.NetflixF16W500.copy(letterSpacing = 0.3.sp)
                )
            }
            VerticalSpacer(space = AppTheme.dimens.grid_2)
            PasswordInput(
                labelText = "New Password",
                isError = state.newPasswordError != null,
                inputValue = state.newPassword,
                onValueChange = { value ->
                    settingsScreenViewModel.onEvent(
                        PasswordChangeEvent.NewPasswordChanged(
                            value
                        )
                    )
                }
            )
            if (state.newPasswordError != null) {
                Text(
                    text = state.newPasswordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End),
                    style = AppTheme.typography.NetflixF16W500.copy(letterSpacing = 0.3.sp)
                )
            }
            VerticalSpacer(space = AppTheme.dimens.grid_2)
            PasswordInput(
                labelText = "Confirm Password",
                isError = state.confirmPasswordError != null,
                inputValue = state.confirmPassword,
                onValueChange = { value ->
                    settingsScreenViewModel.onEvent(
                        PasswordChangeEvent.ConfirmPasswordChanged(
                            value
                        )
                    )
                }
            )
            if (state.confirmPasswordError != null) {
                Text(
                    text = state.confirmPasswordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End),
                    style = AppTheme.typography.NetflixF16W500.copy(letterSpacing = 0.3.sp)
                )
            }
            VerticalSpacer(space = AppTheme.dimens.grid_2)
            OutlinedButton(
                onClick = {
                    settingsScreenViewModel.onEvent(PasswordChangeEvent.Submit)
//                            settingsScreenViewModel.changePassword(
//                                currentPassword = currentPassword,
//                                newPassword = newPassword,
//                                confirmPassword = confirmPassword,
//                            )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.profileHighLightColor,
                    contentColor = AppTheme.colors.onProfileButtonColor
                )
            ) {
                Text(text = "Change Password", style = AppTheme.typography.NetflixF18W500)
            }

        }
    }
}

@Composable
fun PasswordInput(
    labelText: String,
    inputValue: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    Text(
        text = labelText,
        style = AppTheme.typography.NetflixF16W400,
        color = AppTheme.colors.editScreenTitleTextColor
    )
    VerticalSpacer(space = AppTheme.dimens.grid_1)
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        //label = { Text(text = labelText) },
        //placeholder = { Text(text = "Name Field") },
        isError = isError,
        value = inputValue, onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AppTheme.colors.profileTextColor,
            cursorColor = AppTheme.colors.profileTextColor
        )
    )
}