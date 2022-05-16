package com.example.presentation.ui.screens.settingsScreen


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_case.user.AuthUseCases
import com.example.domain.use_case.user.ValidateUseCases
import com.example.presentation.model.PasswordChangeEvent
import com.example.presentation.model.PasswordChangeState
import com.example.presentation.model.UserPreferences
import com.example.utility.TAG
import com.example.utility.UserPreferenceData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val authUseCases: AuthUseCases,
    private val userPreferenceData: UserPreferenceData
) : ViewModel() {
    var userPreferences by mutableStateOf<UserPreferences?>(null)

    init {
        Log.d(TAG, "SettingsScreenViewModel has been created")
        getUserPreferences()
    }

    var state by mutableStateOf(PasswordChangeState())

//    var validationEventChannel by mutableStateOf<ValidationEvent>(ValidationEvent.Idle)
//        private set


    fun singOut() {
        viewModelScope.launch {
            authUseCases.signOut()
        }
    }

    private fun getUserPreferences() {
        viewModelScope.launch {
            userPreferenceData.getUserData.collect { userPref ->
                userPreferences = userPref
            }
            //Log.d("Settings", "email the user has entered $email")
        }
    }

    fun changeDarkMode(value: Boolean) {
        viewModelScope.launch {
            userPreferenceData.changeDarkMode(value)
        }
    }

    fun changeNotification(value: Boolean) {
        viewModelScope.launch {
            userPreferenceData.changeNotification(value)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Settings", "SettingsScreenViewModel has been destroyed")
    }


    fun onEvent(event: PasswordChangeEvent) {
        when (event) {
            is PasswordChangeEvent.PasswordChanged -> {
                state = state.copy(currentPassword = event.currentPassword)
            }

            is PasswordChangeEvent.NewPasswordChanged -> {
                state = state.copy(newPassword = event.newPassword)

            }
            is PasswordChangeEvent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }
            is PasswordChangeEvent.Submit -> {
                changePassword()
            }
        }
    }

    fun changeDefault() {
        state = state.copy(
            currentPasswordError = null,
            newPasswordError = null,
            confirmPasswordError = null
        )
    }

    fun changePassword() {
        val currentPasswordResult = validateUseCases.validatePassword(state.currentPassword)
        val newPasswordResult = validateUseCases.validatePasswordAndNewPassword(
            state.currentPassword,
            state.newPassword
        )
        val confirmPasswordResult =
            validateUseCases.validateRepeatedPassword(state.newPassword, state.confirmPassword)


        val hasError = listOf(
            currentPasswordResult,
            newPasswordResult,
            confirmPasswordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                currentPasswordError = currentPasswordResult.errorMessage,
                newPasswordError = newPasswordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            changeDefault()
            authUseCases.changePassword(
                currentPassword = state.currentPassword,
                newPassword = state.newPassword
            )
        }
//        viewModelScope.launch {
//            authUseCases.changePassword(
//                currentPassword = currentPassword,
//                newPassword = newPassword,
//                confirmPassword = confirmPassword
//            )
//        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
        object Idle : ValidationEvent()
    }
}