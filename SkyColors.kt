package com.example.eazresume

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Enum class representing different parts of the day.
 */
enum class DayPart {
    SUNRISE, DAY, SUNSET, NIGHT
}

@Composable
fun getSkyColors(currentDayPart: DayPart, partDuration: Int): List<Color> {
    val colorValues = listOf(
        mapOf(
            DayPart.SUNRISE to Color(0xFF2D1D7A),
            DayPart.DAY to Color(0xFF00CCFF),
            DayPart.SUNSET to Color(0xFF5B3E6A),
            DayPart.NIGHT to Color(0xFF0A0F29)
        ),
        mapOf(
            DayPart.SUNRISE to Color(0xFF573170),
            DayPart.DAY to Color(0xFF35D7FE),
            DayPart.SUNSET to Color(0xFF874962),
            DayPart.NIGHT to Color(0xFF0E122D)
        ),
        mapOf(
            DayPart.SUNRISE to Color(0xFF804565),
            DayPart.DAY to Color(0xFF69E2FF),
            DayPart.SUNSET to Color(0xFFBA5A5B),
            DayPart.NIGHT to Color(0xFF121B3A)
        ),
        mapOf(
            DayPart.SUNRISE to Color(0xFFAA585B),
            DayPart.DAY to Color(0xFF9FECFF),
            DayPart.SUNSET to Color(0xFFDE741E),
            DayPart.NIGHT to Color(0xFF1A1F47)
        ),
        mapOf(
            DayPart.SUNRISE to Color(0xFFD36C50),
            DayPart.DAY to Color(0xFFD2F7FF),
            DayPart.SUNSET to Color(0xFFFFA739),
            DayPart.NIGHT to Color(0xFF1E2251)
        )
    )

    return colorValues.map { colors ->
        animateColorAsState(
            targetValue = colors[currentDayPart] ?: Color.Black, // Fallback color
            animationSpec = tween(durationMillis = partDuration),
            label = ""
        ).value
    }
}
