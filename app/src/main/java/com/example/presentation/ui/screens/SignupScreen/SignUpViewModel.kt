package com.example.presentation.ui.screens.SignupScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserProfile
import com.example.utility.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val firebase: Firebase,
) : ViewModel() {

    init {
        Log.d(TAG, "The SingUpViewModel has been created")
    }

    var loadingState by mutableStateOf<State<FirebaseUser?>>(State.idle())
        private set

    suspend fun signUp(email: String, password: String) {
        val db = firebase.firestore
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isEmpty() || password.isEmpty()) {
                val message = "The email or password field is empty"
                loadingState = State.failed(message)
            } else {
                try {
                    loadingState = State.loading()
                    val user = firebase.auth.createUserWithEmailAndPassword(email, password).await().user
                    user?.let {
                        val profile = UserProfile(
                            uid = it.uid,
                            email =  it.email ?: ""
                        )
                        db.collection("users").document(it.uid)
                            .set(profile, SetOptions.merge()).await()
                    }
                    //sendVerificationEmail(user)
                    loadingState = State.success(user)
                    Log.d(TAG, "User signed Up by firebase auth $user")
                } catch (e: Exception) {
                    val customErrorMessage = e.firebaseException()
                    loadingState = State.failed(customErrorMessage)
                    Log.d(EXCEPTION, "createUserWithEmail:failure ${e.localizedMessage}")
                }
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