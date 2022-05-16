package com.example.presentation.ui.screens.SignupScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_case.user.AuthUseCases
import com.example.utility.*
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authUseCases: AuthUseCases,
    val userPreferenceData: UserPreferenceData
) : ViewModel() {

    init {
        Log.d(TAG, "The SingUpViewModel has been created")
    }

    var loadingState by mutableStateOf<State<Boolean?>>(State.idle())
        private set

    suspend fun signUp(email: String, password: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            authUseCases.signUpWithEmailUser(email, password)
//                .collect { value -> loadingState = value }
//        }
        viewModelScope.launch(Dispatchers.IO) {
            authUseCases.signUpWithEmailUser(email, password)
                .collect { value ->
                    when (value) {
                        is State.Success -> {
                            userPreferenceData.changeEmail(email)
                        }
                        else -> {}
                    }
                    loadingState = value
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "The SingUpViewModel has been destroyed")
    }

    private suspend fun sendVerificationEmail(user: FirebaseUser?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                user!!.sendEmailVerification().await()
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d(EXCEPTION, it) }
            }
        }
    }
}