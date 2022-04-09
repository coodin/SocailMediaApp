package com.example.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration


@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

//private val LocalAppDimens = staticCompositionLocalOf {
//    smallDimensions
//}

//
//private val LocalAppColors = staticCompositionLocalOf {
//    extendedColors
//}


private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun LoginSignUpComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val configuration = LocalConfiguration.current
    val dimensions = if (configuration.screenWidthDp <= 360) smallDimensions else sw360Dimensions
    //val typography = if (configuration.screenWidthDp <= 360) smallTypography else sw360Typography
    CompositionLocalProvider(
        LocalAppDimens provides dimensions,
        LocalExtendedColors provides extendedColors,
        LocalAppTypography provides extraTypography
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object AppTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes
    val typography: CustomTypography
        @Composable
        get() = LocalAppTypography.current
}
