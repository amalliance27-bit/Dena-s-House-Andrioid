package com.example.builtbyd.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BuiltGold,
    onPrimary = BuiltBlack,
    primaryContainer = BuiltBurgundyDeep,
    onPrimaryContainer = BuiltGoldLight,
    secondary = BuiltGoldLight,
    onSecondary = BuiltBlack,
    background = BuiltBlack,
    onBackground = BuiltIvory,
    surface = BuiltCharcoal,
    onSurface = BuiltIvory,
    surfaceVariant = BuiltCardBg,
    onSurfaceVariant = BuiltIvorySoft,
    outline = BuiltLine
)

private val LightColorScheme = lightColorScheme(
    primary = BuiltBurgundy,
    onPrimary = BuiltIvory,
    primaryContainer = BuiltBurgundyDeep,
    onPrimaryContainer = BuiltGoldLight,
    secondary = BuiltGold,
    onSecondary = BuiltBlack,
    background = BuiltIvory,
    onBackground = BuiltInk,
    surface = BuiltIvorySoft,
    onSurface = BuiltInk,
    surfaceVariant = BuiltCardLightBg,
    onSurfaceVariant = BuiltInk,
    outline = BuiltLine
)

@Composable
fun BuiltByDTheme(
    darkTheme: Boolean = true, // Default to brand dark luxury theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BuiltBlack.toArgb()
            window.navigationBarColor = BuiltBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
