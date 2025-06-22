package com.example.temanbelajar.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography

private val LightColors = lightColorScheme(
    primary = BrownMedium,
    onPrimary = CreamSoft,
    background = CreamSoft,
    surface = CreamMedium,
    onBackground = BrownDark,
    onSurface = BrownDark
)

private val DarkColors = darkColorScheme(
    primary = BrownLight,
    onPrimary = CreamSoft,
    background = BrownDark,
    surface = BrownMedium,
    onBackground = CreamSoft,
    onSurface = CreamSoft
)

@Composable
fun TemanbelajarTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors: ColorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}
