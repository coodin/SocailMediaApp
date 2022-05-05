package com.example.presentation.ui.screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun TemporaryScreen(loading: Boolean) {
    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .7f))
                .then(if (loading) Modifier.pointerInput(Unit) {} else Modifier),
            contentAlignment = Alignment.Center
        )
        {
            CircularProgressIndicator(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxSize(0.3f),
                color = Color.Blue.copy(alpha = 0.4f),
                strokeWidth = 8.dp
            )
        }
    }
}

@Composable
fun AutoSizeText(text: String, textStyle: TextStyle) {
    var scaledTextStyle by remember { mutableStateOf(textStyle) }
    var readyToDraw by remember { mutableStateOf(false) }
    Text(
        text = text,
        modifier = Modifier.drawWithContent {
            if (readyToDraw) {
                drawContent()
            }
        },
        softWrap = false,
        style = scaledTextStyle,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                val fraction = textLayoutResult.size.width / textLayoutResult.multiParagraph.width
                scaledTextStyle =
                    scaledTextStyle.copy(fontSize = scaledTextStyle.fontSize * fraction)
            } else {
                readyToDraw = true
            }
        }
    )

}

@Composable
fun VerticalSpacer(space: Dp) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun HorizontalSpacer(space: Dp) {
    Spacer(modifier = Modifier.width(space))
}