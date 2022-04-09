package com.example.presentation.ui.screens.favortieScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.utility.TAG

class FavoriteViewModel:ViewModel() {
    init {
        Log.d(TAG,"Favorite Viewmodel has been started")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"Favorite Viewmodel has been destroyed")
    }
}