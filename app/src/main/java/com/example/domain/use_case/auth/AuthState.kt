package com.example.domain.use_case.auth

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow


class AuthState(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return authRepository.getFirebaseAuthState()
    }
}