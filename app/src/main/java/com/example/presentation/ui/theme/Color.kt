package com.example.presentation.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.google.errorprone.annotations.Immutable


@Immutable
data class ExtendedColors(
    val profileHighLightColor: Color,
    val onProfileButtonColor: Color,
    val addFriendBackgroundColor: Color,
    val profileTextColor: Color,
    val profileSoftTextColor: Color,
    val lineGrayColor: Color,
    val chatBackgroundColor: Color

)

val LocalExtendedColors = staticCompositionLocalOf {
    extendedColors
}

val extendedColors = ExtendedColors(
    profileHighLightColor = Color("#5458F7".toColorInt()),
    onProfileButtonColor = Color.White,
    profileTextColor = Color.Black,
    profileSoftTextColor = Color("#9597A1".toColorInt()),
    addFriendBackgroundColor = Color.White,
    lineGrayColor = Color("#F2F2F2".toColorInt()),
    chatBackgroundColor = Color("#979797".toColorInt())
)


val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF4548C9)


val facebookColor = Color("#1877F2".toColorInt())

val ExtendedColors.colorOfFacebook: Color
    get() = facebookColor