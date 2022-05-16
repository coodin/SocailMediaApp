package com.example.domain.use_case.auth

import com.example.domain.repository.AuthRepository

class ChangePassword(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String
    ) {
        authRepository.changePassword(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
    }
}