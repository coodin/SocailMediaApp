package com.example.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserProfile
import com.example.domain.use_case.user.AuthUseCases
import com.example.domain.use_case.user.UserUseCases
import com.example.utility.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val userUseCases: UserUseCases
) : ViewModel() {
    var isCurrent by mutableStateOf<Boolean?>(null)
        private set
    var userState by mutableStateOf<State<UserProfile?>?>(null)
        private set

    init {
        //isLoggedIn()
        //authState()
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            isCurrent = authUseCases.currentUser()
        }
    }

     fun authState() {
        viewModelScope.launch {
            authUseCases.authState().collect { value -> isCurrent = value }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            userUseCases.getUser().collect { value -> userState = value }
        }
    }

    fun signOut(){
        authUseCases.signOut()
    }

}