package com.example.domain.use_case

import com.example.domain.model.UserProfile
import com.example.domain.repository.UserRepository
import com.example.utility.State
import kotlinx.coroutines.flow.Flow

class CurrenUser(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.currentUser()
    }
}