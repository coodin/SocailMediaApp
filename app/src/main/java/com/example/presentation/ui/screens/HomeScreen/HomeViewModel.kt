package com.example.presentation.ui.screens.HomeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.City
import com.example.domain.model.UserProfile
import com.example.domain.use_case.user.AuthUseCases
import com.example.domain.use_case.user.UserUseCases
import com.example.utility.State
import com.example.utility.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userUseCases: UserUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {
    // private val repository = FirebaseRepository()

    //    private val _dataset = MutableStateFlow<ResultOf<QuerySnapshot?>?>(null)
//    val dataset:StateFlow<ResultOf<QuerySnapshot?>?> = _dataset
    var usersState by mutableStateOf<State<List<UserProfile?>>?>(null)
        private set
//    var userState by mutableStateOf<State<UserProfile?>?>(null)
//        private set
    var documentState by mutableStateOf<State<City?>?>(null)
        private set
    var isCurrent by mutableStateOf<State<Boolean>?>(null)
        private set

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "The HomeViewModel has been destroyed")
    }

    init {
        Log.d(TAG, "The HomeViewModel has been created")
        //getUser()
    }

    private fun getUsers() {
        viewModelScope.launch {
            userUseCases.getUsers().collect { value -> usersState = value }
        }
    }

//    private fun getUser() {
//        viewModelScope.launch {
//            userUseCases.getUser().collect { value -> userState = value }
//        }
//    }

//    fun signOut() {
//        viewModelScope.launch {
//            authUseCases.signOut().collect { value -> isCurrent = value }
//        }
//    }


//    fun onStop() {
//        job?.cancel()
//    }

//    fun onStart() {
//        job = viewModelScope.launch {
//            try {
//                repository.getDocuments().collect { state ->
//                    usersState = state
//                }
//            } catch (e: Throwable) {
//                Log.d(EXCEPTION, "Exception $e")
//            }
//        }
//    }


//    fun addDocument() {
//        viewModelScope.launch {
//            repository.addDifferentTypes()
//        }
//    }
//
//    fun addCity() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addCity()
//        }
//    }
//
//    fun updateCity() {
//        viewModelScope.launch {
//            repository.updateCity()
//        }
//    }
//
//    fun transitionUpdate() {
//        viewModelScope.launch {
//            repository.transitionUpdate()
//        }
//    }
//
//    fun updateTimeStamp() {
//        repository.updateServerTimeStamp()
//    }
//
//    fun deleteDocument() {
//        viewModelScope.launch {
//            delay(3000)
//            repository.deleteDocument()
//        }
//    }
//
//
//    // Get operations
//    fun getDocument() {
//        viewModelScope.launch {
//            repository.getDocument().collect {
//                documentState = it
//            }
//        }
//    }


}

//class MyViewModelFactory(private val activity: Activity) : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(activity) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}