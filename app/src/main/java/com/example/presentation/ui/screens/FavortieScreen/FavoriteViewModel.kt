package com.example.presentation.ui.screens.FavortieScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.utility.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    //private val useCases: UseCases
): ViewModel() {
    init {
        Log.d(TAG,"Favorite Viewmodel has been started")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"Favorite Viewmodel has been destroyed")
    }
}