package com.example.domain.use_case


import com.example.domain.model.UserProfile
import com.example.domain.repository.UserRepository
import com.example.utility.State
import kotlinx.coroutines.flow.Flow

class GetUser(
    private val userRepository: UserRepository
) {
    var user: Flow<State<UserProfile?>>? = null
    suspend operator fun invoke(userID: String): Flow<State<UserProfile?>> {
        user = userRepository.getUser(userID)
        return userRepository.getUser(userID)
    }
}