package com.example.domain.use_case.user

import com.example.domain.repository.UserRepository

class GetUsers(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getUsers()
}