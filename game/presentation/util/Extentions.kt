package com.example.eazresume.game.presentation.util

import android.util.Log
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class ScrollDestination {
    START,
    END,
    NONE,
}

suspend fun LazyListState.smoothScrollToDestination(
    destination: ScrollDestination,
    speedMultiplier: Int = 1
) {
    if (destination == ScrollDestination.NONE) return

    // Calculate the scroll duration and distance per frame for smooth animation
    val totalItemsCount = layoutInfo.totalItemsCount
    val lastItemIndex = totalItemsCount - 1
    val durationMillis = speedMultiplier * 15000L // Total scroll duration in milliseconds
    val frameDurationMillis = 16L // Frame duration for smooth animation (8ms for ~60fps)

    val totalScrollDistance = layoutInfo.visibleItemsInfo.lastOrNull()?.size?.times(lastItemIndex) ?: 0
    val distancePerFrame = if (totalScrollDistance > 0) {
        totalScrollDistance.toFloat() / (durationMillis / frameDurationMillis)
    } else {
        // Default to a minimum scroll distance to avoid division by zero
        1f
    }

    // Logging the calculated values for debugging purposes
    Log.d(
        "SmoothScroll",
        "Total Items: $totalItemsCount, Last Item Index: $lastItemIndex, Total Distance: $totalScrollDistance, Distance per Frame: $distancePerFrame"
    )

    val initialOffset = firstVisibleItemScrollOffset.toFloat()
    val targetOffset = when (destination) {
        ScrollDestination.END -> totalScrollDistance.toFloat()
        ScrollDestination.START -> 0f
        ScrollDestination.NONE -> return
    }

    val scrollDirection = if (destination == ScrollDestination.END) 1 else -1
    var currentOffset = initialOffset

    // Log initial and target offsets for debugging
    Log.d("SmoothScroll", "Initial Offset: $initialOffset, Target Offset: $targetOffset")

    // Scroll until the destination is reached
    while (
        (destination == ScrollDestination.END && currentOffset < targetOffset) ||
        (destination == ScrollDestination.START && currentOffset > targetOffset)
    ) {
        val distanceToScroll = scrollDirection * distancePerFrame
        Log.d("SmoothScroll", "Scrolling by $distanceToScroll")

        scrollBy(distanceToScroll)
        currentOffset += distanceToScroll

        delay(frameDurationMillis)

        // Adjust the current offset to match the actual list state and prevent overshooting
        currentOffset = firstVisibleItemScrollOffset.toFloat()
    }

    Log.d("SmoothScroll", "Scrolling complete")
}

fun Random.getRandomFloatInRange(min: Float, max: Float): Float {
    return min + (max - min) * Random.nextFloat()
}

fun Random.getRandomIntInRange(min: Int, max: Int): Int {
    return Random.nextInt(min, max + 1)
}

fun Modifier.randomObjectSize(minHeight: Int = 40, maxHeight: Int = 120): Modifier {
    val randomHeightSize = Random.nextInt(minHeight, maxHeight + 1)
    return this.size(width = (randomHeightSize * 1.5).dp, height = randomHeightSize.dp)
}
