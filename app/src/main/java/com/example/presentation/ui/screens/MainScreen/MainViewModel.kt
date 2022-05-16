package com.example.presentation.ui.screens.MainScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserProfile
import com.example.domain.use_case.user.GetUsers
import com.example.domain.use_case.user.UserUseCases
import com.example.utility.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: UserUseCases
) : ViewModel() {
    var userState by mutableStateOf<State<UserProfile?>?>(null)
        private set
    val userID = savedStateHandle.getStateFlow("userID", "ID").value

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            useCase.getUser().collect { data -> userState = data }
        }
    }
}