package com.example.presentation.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.presentation.ui.theme.AppTheme

@Composable
fun GoogleOrFacebook(
    text: String,
    icon: Painter,
    textStyle: TextStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.DarkGray
) {
    Button(
        onClick = { /*TODO*/ }, modifier = Modifier
            .height(height = AppTheme.dimens.grid_2_5 * 3)
            .fillMaxWidth(), colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor, contentColor = contentColor
        ),
        shape = RoundedCornerShape(20)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(AppTheme.dimens.grid_1_5))
            Image(
                painter = icon,
                contentDescription = text
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.grid_1_5))
//            Text(
//                text = text,
//                modifier = Modifier.drawWithContent {
//                    if (readyToDraw) {
//                        drawContent()
//                    }
//                },
//                softWrap = false,
//                style = scaledTextStyle,
//                onTextLayout = { textLayoutResult ->
//                    if (textLayoutResult.didOverflowWidth) {
//                        val fraction = textLayoutResult.size.width / textLayoutResult.multiParagraph.width
//                        scaledTextStyle =
//                            scaledTextStyle.copy(fontSize = scaledTextStyle.fontSize * fraction)
//                    } else {
//                        readyToDraw = true
//                    }
//                }
//            )
            AutoSizeText(text, textStyle)
        }
    }
}