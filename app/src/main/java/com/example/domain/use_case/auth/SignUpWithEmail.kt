package com.example.domain.use_case.auth

import com.example.domain.repository.AuthRepository
import com.example.utility.State
import kotlinx.coroutines.flow.Flow

class SignUpWithEmail(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<State<Boolean>> {
        return authRepository.firebaseSignUpWithEmail(email, password)
    }
}