package com.example.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.loginsignupcompose.R
import com.google.errorprone.annotations.Immutable

val Rubik = FontFamily(
    Font(R.font.rubik_regular),
    Font(R.font.rubik_medium, FontWeight.W500),
    Font(R.font.rubik_bold, FontWeight.Bold)
)

val Netflix = FontFamily(
    Font(R.font.netflix_sans_medium, FontWeight.Medium),
    Font(R.font.netflix_sans_regular, FontWeight.Normal)
)


@Immutable
data class CustomTypography(
    val NetflixF10W400: TextStyle,
    val NetflixF12W400: TextStyle,
    val NetflixF12W500: TextStyle,
    val NetflixF14W400: TextStyle,
    val NetflixF16W400: TextStyle,
    val NetflixF16W500: TextStyle,
    val NetflixF18W400: TextStyle,
    val NetflixF14W500: TextStyle,
    val NetflixF18W500: TextStyle,
    val NetflixF20W500: TextStyle,
    val NetflixF26W500: TextStyle
)

val LocalAppTypography = staticCompositionLocalOf {
    extraTypography
}


val extraTypography = CustomTypography(
    NetflixF10W400 = TextStyle(
        fontFamily = Netflix,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal
    ),
    NetflixF12W400 = TextStyle(
        fontFamily = Netflix,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    NetflixF12W500 = TextStyle(
        fontFamily = Netflix,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    ),
    NetflixF14W400 = TextStyle(
        fontFamily = Netflix,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    NetflixF16W400 = TextStyle(
        fontFamily = Netflix,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    NetflixF16W500 = TextStyle(
        fontFamily = Netflix,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    NetflixF18W400 = TextStyle(
        fontFamily = Netflix,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    NetflixF14W500 = TextStyle(
        fontFamily = Netflix,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),
    NetflixF18W500 = TextStyle(
        fontFamily = Netflix,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    ),
    NetflixF20W500 = TextStyle(
        fontFamily = Netflix,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    ),
    NetflixF26W500 = TextStyle(
        fontFamily = Netflix,
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium
    ),
)


// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.W300,
        fontSize = 96.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        letterSpacing = (-0.5).sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    body2 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    )
/* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)