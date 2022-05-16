package com.example.presentation.ui.screens.LoginScreen


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_case.user.AuthUseCases
import com.example.utility.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
   // private val savedStateHandle: SavedStateHandle,
    private val authUseCases: AuthUseCases
) : ViewModel() {
    init {
        Log.d(TAG, "The LoginViewModel has been created")
    }

    var loadingState by mutableStateOf<State<Boolean?>>(State.idle())
        private set


    suspend fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authUseCases.signInWithEmail(email, password).collect { value -> loadingState = value }
        }
    }

    fun changeToDefault() {
        loadingState = State.idle()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "The LoginViewModel has been destroyed")
    }
}