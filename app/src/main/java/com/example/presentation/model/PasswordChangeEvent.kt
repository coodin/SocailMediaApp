package com.example.presentation.model

sealed class PasswordChangeEvent {
    class PasswordChanged(val currentPassword: String) : PasswordChangeEvent()
    class NewPasswordChanged(val newPassword: String) : PasswordChangeEvent()
    class ConfirmPasswordChanged(val confirmPassword: String) : PasswordChangeEvent()

    object Submit : PasswordChangeEvent()
}
