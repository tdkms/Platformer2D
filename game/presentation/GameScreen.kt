package com.example.eazresume.game.presentation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eazresume.SkyViewModel
import com.example.eazresume.game.presentation.components.GameSurface
import com.example.eazresume.game.presentation.components.SkyScene
import com.example.eazresume.game.presentation.util.ScrollDestination
import com.example.eazresume.game.presentation.util.smoothScrollToDestination

@Composable
fun GameScreen() {
    val skyViewModel = viewModel<SkyViewModel>()
    val skyState = rememberLazyListState()
    val surfaceState = rememberLazyListState()

    val config = LocalConfiguration.current
    val heightWidth = config.screenHeightDp
    val surfaceHeight = heightWidth / 5

    // movement (Forward/Backward)
    val forwardInteractionSource = remember { MutableInteractionSource() }
    val backwardInteractionSource = remember { MutableInteractionSource() }
    val isForwardPressed by forwardInteractionSource.collectIsPressedAsState()
    val isBackwardPressed by backwardInteractionSource.collectIsPressedAsState()

    // Track the movement direction
    val destination: ScrollDestination = when {
        isForwardPressed -> ScrollDestination.END
        isBackwardPressed -> ScrollDestination.START
        else -> ScrollDestination.NONE
    }
    // If you want to add objects on the surface, use Box instead of Column.
    Column(modifier = Modifier.fillMaxSize()) {
        SkyScene(
            modifier = Modifier.weight(1f),
            destination = destination,
            forwardSource = forwardInteractionSource,
            backwardSource = backwardInteractionSource,
            listState = skyState,
            currentDayPart = skyViewModel.currentDayPart,
            partDuration = skyViewModel.partDurationMs
        )
        GameSurface(
            modifier = Modifier.height(surfaceHeight.dp),
            listState = surfaceState,
        )
    }

    // Scroll  sky
    LaunchedEffect(destination) {
        destination.let { direction ->
            skyState.smoothScrollToDestination(destination = direction, speedMultiplier = 10)
        }
    }
    // Scroll  surface
    LaunchedEffect(destination) {
        destination.let { direction ->
            surfaceState.smoothScrollToDestination(destination = direction)
        }
    }
}
