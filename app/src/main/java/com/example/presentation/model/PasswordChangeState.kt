package com.example.presentation.model

import com.example.domain.use_case.auth.ChangePassword

data class PasswordChangeState(
    val currentPassword: String = "",
    val currentPasswordError: String? = null,
    val newPassword: String = "",
    val newPasswordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null
)
