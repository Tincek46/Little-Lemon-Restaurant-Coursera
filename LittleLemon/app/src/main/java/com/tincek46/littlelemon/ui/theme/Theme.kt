package com.tincek46.littlelemon.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LittleLemonYellow,
    onPrimary = LittleLemonCharcoal,
    primaryContainer = LittleLemonGreen,
    onPrimaryContainer = LittleLemonCloud,
    secondary = LittleLemonPeach,
    onSecondary = LittleLemonCharcoal,
    secondaryContainer = Color(0xFF5E4030), // Darker Peach
    onSecondaryContainer = LittleLemonLightPeach,
    tertiary = LittleLemonLightPeach,
    onTertiary = LittleLemonCharcoal,
    tertiaryContainer = Color(0xFF634A3E), // Darker version of LittleLemonLightPeach
    onTertiaryContainer = White,
    error = LittleLemonErrorRedDark, // Corrected
    onError = LittleLemonCharcoal,   // Ensure contrast with LittleLemonErrorRedDark
    errorContainer = LittleLemonErrorRedDark.copy(alpha = 0.2f), // Replacement
    onErrorContainer = LittleLemonCloud, // Replacement
    background = LittleLemonCharcoal,
    onBackground = LittleLemonCloud,
    surface = Color(0xFF2C2C2C), // A custom dark surface
    onSurface = LittleLemonCloud,
    surfaceVariant = LittleLemonGreen,
    onSurfaceVariant = LittleLemonCloud,
    outline = LittleLemonGreen.copy(alpha = 0.5f), // Replacement
    inverseOnSurface = LittleLemonCharcoal,
    inverseSurface = LittleLemonCloud,
    inversePrimary = LittleLemonGreen,
    surfaceTint = LittleLemonYellow,
    outlineVariant = LittleLemonGreen.copy(alpha = 0.3f), // Replacement
    scrim = Black
)

private val LightColorScheme = lightColorScheme(
    primary = LittleLemonGreen,
    onPrimary = White,
    primaryContainer = LittleLemonLightPeach,
    onPrimaryContainer = LittleLemonCharcoal,
    secondary = LittleLemonYellow,
    onSecondary = LittleLemonCharcoal,
    secondaryContainer = LittleLemonYellow,
    onSecondaryContainer = LittleLemonCharcoal,
    tertiary = LittleLemonPeach,
    onTertiary = White,
    tertiaryContainer = LittleLemonLightPeach,
    onTertiaryContainer = LittleLemonCharcoal,
    error = LittleLemonErrorRed,
    onError = White,
    errorContainer = LittleLemonErrorRed.copy(alpha = 0.1f), // Replacement
    onErrorContainer = LittleLemonCharcoal, // Replacement
    background = LittleLemonCloud,
    onBackground = LittleLemonCharcoal,
    surface = White,
    onSurface = LittleLemonCharcoal,
    surfaceVariant = LittleLemonCloud,
    onSurfaceVariant = LittleLemonCharcoal,
    outline = LittleLemonCharcoal.copy(alpha = 0.5f), // Replacement
    inverseOnSurface = LittleLemonCloud,
    inverseSurface = LittleLemonCharcoal,
    inversePrimary = LittleLemonYellow,
    surfaceTint = LittleLemonGreen,
    outlineVariant = LittleLemonCharcoal.copy(alpha = 0.25f), // Replacement
    scrim = Black
)

@Composable
fun LittleLemonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Defaulting to false for consistent branding
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
        typography = Typography,
        content = content
    )
}
