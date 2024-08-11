package com.example.eazresume

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for managing and updating the sky color based on the time of day.
 */


class SkyViewModel : ViewModel() {

    private val fullCycleDurationMs = 10000 * 24 // 24 hours expressed in milliseconds
    val partDurationMs = fullCycleDurationMs / DayPart.entries.size

    // State holding the current part of the day, mutable only within this ViewModel
    var currentDayPart by mutableStateOf(DayPart.NIGHT)
        private set

    init {
        viewModelScope.launch {
            while (true) {
                val currentTimeInCycleMs = System.currentTimeMillis() % fullCycleDurationMs
                val currentPartIndex = (currentTimeInCycleMs / partDurationMs).toInt()

                currentDayPart = when (currentPartIndex) {
                    0 -> DayPart.SUNRISE
                    1 -> DayPart.DAY
                    2 -> DayPart.SUNSET
                    else -> DayPart.NIGHT
                }

                val nextPartStartTimeMs = ((currentPartIndex + 1) * partDurationMs).toLong()
                val delayDurationMs = nextPartStartTimeMs - currentTimeInCycleMs

                // Delay until the start of the next part of the day
                delay(delayDurationMs)
            }
        }
    }
}
