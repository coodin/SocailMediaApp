package com.example.domain.use_case.auth

import com.example.domain.repository.AuthRepository
import com.example.utility.State
import kotlinx.coroutines.flow.Flow

class SignOutUser(
    private val authRepository: AuthRepository
) {
    operator fun invoke(){
        return authRepository.signOut()
    }
}