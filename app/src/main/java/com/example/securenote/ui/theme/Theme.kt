package com.example.securenote.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val GreenPrimary = Color(0xFF529873)

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    secondary = Color(0xFF424242),
    onSecondary = Color.White,
    onPrimaryContainer = Color(0xFF5B5959)
)

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.Black,
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF7F7F7),
    onSurface = Color(0xFF212121),
    secondary = Color(0xFFE0E0E0),
    onSecondary = Color.Black,
    onPrimaryContainer = Color(0xFF838383)
)

private val AppTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontFamily = FontFamily.Default,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily.Default,
        textAlign = TextAlign.Center
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily.Default,
        textAlign = TextAlign.Center
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily.Default,
        textAlign = TextAlign.Center
    )
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun SecureNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    SetSystemBarsColor(colorScheme, useDarkIcons = !darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
        shapes = AppShapes
    )
}

@Composable
fun SetSystemBarsColor(colorScheme: ColorScheme, useDarkIcons: Boolean) {
    val view = LocalView.current
    val context = LocalContext.current
    SideEffect {
        val window = (context as Activity).window
        window.navigationBarColor = colorScheme.background.toArgb()
        window.statusBarColor = colorScheme.background.toArgb()

        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
            useDarkIcons
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkIcons
    }
}

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5