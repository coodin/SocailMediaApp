package com.example.presentation.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.example.loginsignupcompose.R
import com.example.presentation.ui.screens.Components.HorizontalSpacer
import com.example.presentation.ui.theme.AppTheme
import com.example.utility.TAG

@Composable
fun CheatScreen() {
    var message by remember {
        mutableStateOf("")
    }
    val listState = rememberLazyListState()
    var isVisible by remember {
        mutableStateOf(false)
    }

    DisposableEffect(key1 = Unit) {
        isVisible = true
        onDispose {
            isVisible = false
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomAppBar(isVisible, message) { newValue -> message = newValue }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.grid_1_5),
            reverseLayout = true
        ) {
            items(listOf(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)) {
                ItemCard()
            }
        }
    }
}

@Composable
fun BottomAppBar(isVisible: Boolean, message: String, onMessageChange: (String) -> Unit) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            animationSpec = spring(
                stiffness = Spring.StiffnessHigh,
                visibilityThreshold = IntOffset.VisibilityThreshold
            ),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = spring(
                stiffness = Spring.StiffnessHigh,
                visibilityThreshold = IntOffset.VisibilityThreshold
            ),
            targetOffsetY = { it }
        ),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = message,
            textStyle = AppTheme.typography.NetflixF16W400,
            onValueChange = { newValue -> onMessageChange(newValue) },
            trailingIcon = {
                IconButton(onClick = { Log.d(TAG, "Trailing Icon pressed") }) {
                    Icons.Filled.Send
                }
            },
            placeholder = { Text(text = "Type Somethink") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = AppTheme.colors.lineGrayColor,
                focusedBorderColor = AppTheme.colors.chatBackgroundColor,
                textColor = AppTheme.colors.addFriendBackgroundColor,
                backgroundColor = AppTheme.colors.profileHighLightColor,
                cursorColor = AppTheme.colors.addFriendBackgroundColor,
                trailingIconColor = AppTheme.colors.profileTextColor
            )
        )
    }
}

@Composable
fun ItemCard() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .size(40.dp),
            painter = painterResource(id = R.drawable.message_photo),
            contentDescription = "Message Avatar"
        )
        HorizontalSpacer(space = AppTheme.dimens.grid_1)
        Surface(
            modifier =
            Modifier
                .wrapContentSize(),
            color = AppTheme.colors.chatBackgroundColor.copy(alpha = 0.1f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier =
                Modifier.padding(
                    horizontal = AppTheme.dimens.grid_1_5,
                    vertical = AppTheme.dimens.grid_1
                )
            ) {
                Text(
                    modifier = Modifier.padding(bottom = AppTheme.dimens.grid_0_5),
                    text = "Name",
                    color = AppTheme.colors.profileTextColor,
                    style = AppTheme.typography.NetflixF18W500
                )
                Text(
                    text = "MessageMessageMessageMessageMessageMessageMessageMessageMessage",
                    color = AppTheme.colors.profileHighLightColor,
                    letterSpacing = 0.6.sp,
                    lineHeight = 1.5.em,
                    style = AppTheme.typography.NetflixF14W400
                )
            }
        }
    }
}