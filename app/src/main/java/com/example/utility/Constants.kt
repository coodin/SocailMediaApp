package com.example.utility

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import com.example.presentation.ui.screens.navHost.NavigationRoutes
import com.example.presentation.ui.theme.AppTheme

const val TAG = "Firebase"
const val EXCEPTION = "AuthException"

val COLOR_NORMAL = Color(0xffEDEFF4)
val COLOR_SELECTED = Color(0xff496DE2)
val ICON_SIZE = 24.dp
val items = listOf(
    NavigationRoutes.Home,
    NavigationRoutes.Fav,
    NavigationRoutes.Feed,
    NavigationRoutes.Profile,
)


fun Color.convertHex(colorString: String): Color {
    return Color(android.graphics.Color.parseColor("#$colorString"))
}

fun Modifier.drawLine(
    color: Color = Color.Black,
    strokeWidth: Float = 1f,
    offsetY: Int?
) =
    drawWithContent {
        drawContent()
//        inset(){
//
//        }
        translate(top = offsetY?.toFloat() ?: 10f) {
            drawLine(
                color = color,
                start = Offset(x = 0f, y = size.height - 1),
                end = Offset(x = size.width, y = size.height - 1),
                strokeWidth = strokeWidth
            )
        }


    }

object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = AppTheme.colors.profileHighLightColor

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,1f)
}