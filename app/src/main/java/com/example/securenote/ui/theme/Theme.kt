package com.example.securenote.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

private val YellowPrimary = Color(0xFFFBC02D)

private val DarkColorScheme = darkColorScheme(
    primary = YellowPrimary,
    onPrimary = Color.Black,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    secondary = Color(0xFF424242),
    onSecondary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = YellowPrimary,
    onPrimary = Color.Black,
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF7F7F7),
    onSurface = Color(0xFF212121),
    secondary = Color(0xFFE0E0E0),
    onSecondary = Color.Black
)

private val AppTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontFamily = FontFamily.Default,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
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


@Composable
fun SecureNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}