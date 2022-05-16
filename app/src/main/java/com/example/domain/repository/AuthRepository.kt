package com.example.domain.repository

import com.example.domain.use_case.user.GetUser
import com.example.utility.State
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    fun signOut()

    suspend fun firebaseSignInWithEmail(email: String, password: String): Flow<State<Boolean>>

    suspend fun firebaseSignUpWithEmail(email: String, password: String): Flow<State<Boolean>>

    fun getFirebaseAuthState(): Flow<Boolean>

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    )
}