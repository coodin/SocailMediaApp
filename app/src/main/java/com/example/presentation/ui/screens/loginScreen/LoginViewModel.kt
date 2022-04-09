package com.example.presentation.ui.screens.loginScreen


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utility.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val firebase: Firebase
) : ViewModel() {
    init {
        Log.d(TAG, "The LoginViewModel has been created")
    }

    var loadingState by mutableStateOf<State<FirebaseUser?>>(State.idle())
        private set

    suspend fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isEmpty() || password.isEmpty()) {
                val message = "The email or password field is empty"
                Log.d(EXCEPTION, message)
                loadingState = State.failed(message)
            } else {
                try {
                    loadingState = State.loading()
                    val user = firebase.auth.signInWithEmailAndPassword(email, password).await().user
                    loadingState = State.success(user)
                } catch (e: Exception) {
                    val customErrorMessage = e.firebaseException()
                    //val errorCode = (e as FirebaseAuthException).errorCode
                    loadingState = State.failed(customErrorMessage)
                    Log.d(EXCEPTION, "createUserWithEmail:failure ${e.localizedMessage}")
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "The LoginViewModel has been destroyed")
    }
}