package com.devshady.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkColorScheme = darkColorScheme(
    primary = RoyalBlue,
    onPrimary = Color.White,
    primaryContainer = DarkGray,
    onPrimaryContainer = Color.White,
    background = OLEDBlack,
    surface = DarkGray,
    onSurface = Color.White,
    onSurfaceVariant = MutedGray,
    surfaceVariant = DarkGray,         // Used by default Card colors
    secondaryContainer = OLEDBlack,    // Common fallback for small containers
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme,
        typography = NewsTypography,
        content = content
    )
}