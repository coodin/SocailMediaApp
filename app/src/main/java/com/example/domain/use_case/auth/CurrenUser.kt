package com.example.domain.use_case

import com.example.domain.repository.AuthRepository


class CurrentUser(
    private val userRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return userRepository.isUserAuthenticatedInFirebase()
    }
}