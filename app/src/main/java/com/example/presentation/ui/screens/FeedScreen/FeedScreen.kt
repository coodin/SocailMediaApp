package com.example.presentation.ui.screens.FeedScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Feed(navigateFeedComposable: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Feed")
        Button(onClick = { navigateFeedComposable() }) {
            Text(text = "Navigate to FeedComposable")
        }
    }
}