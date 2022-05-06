package com.example.domain.repository

import com.example.domain.model.UserProfile
import com.example.utility.State
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<State<List<UserProfile?>>>
    suspend fun getUser(): Flow<State<UserProfile?>>
    suspend fun currentUser(): Boolean
}