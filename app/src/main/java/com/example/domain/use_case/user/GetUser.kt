package com.example.domain.use_case.user


import com.example.domain.model.UserProfile
import com.example.domain.repository.UserRepository
import com.example.utility.State
import kotlinx.coroutines.flow.Flow

class GetUser(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<State<UserProfile?>> {
        return userRepository.getUser()
    }
}