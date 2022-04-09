package com.example.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.FirebaseRepository
import com.example.domain.model.City
import com.example.domain.model.UserProfile
import com.example.domain.use_case.UseCases
import com.example.utility.EXCEPTION
import com.example.utility.State
import com.example.utility.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCases: UseCases
) : ViewModel() {
    // private val repository = FirebaseRepository()

    //    private val _dataset = MutableStateFlow<ResultOf<QuerySnapshot?>?>(null)
//    val dataset:StateFlow<ResultOf<QuerySnapshot?>?> = _dataset
    var usersState by mutableStateOf<State<List<UserProfile?>>?>(null)
        private set
    var documentState by mutableStateOf<State<City?>?>(null)
        private set
    //var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "The MainViewModel has been destroyed")
    }

    init {
        Log.d(TAG, "The MainViewModel has been created")
        Log.d(TAG, "The User UID: " + savedStateHandle.get<String>("ID"))
        getUsers()
//        job = viewModelScope.launch {
//            try {
//                repository.getDocuments().collect { state ->
//                    usersState = state
//                }
//            } catch (e: Throwable) {
//                Log.d(EXCEPTION, "Exception $e")
//            }
//        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            useCases.getUsers().collect { value -> usersState = value }
        }
    }

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