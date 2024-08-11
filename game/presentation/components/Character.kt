package com.example.eazresume.game.presentation.components

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.eazresume.R
import com.example.eazresume.game.presentation.util.ScrollDestination
import kotlinx.coroutines.delay


@Composable
fun Character(
    destination: ScrollDestination = ScrollDestination.NONE,
    // height of surface
    jump: Boolean = false,
    itIsLanded: () -> Unit
) {
    val groundHeight = 20.dp
    val characterHeight = 220.dp
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp * 2
    val displayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels
    val startBorder = screenWidth * 1 / 8
    val endBorder = screenWidth * 7 / 8
    var lastDestination by remember { mutableStateOf(ScrollDestination.END) }

    // Initial Y position (starting from the top of the screen)
    var characterY by remember { mutableFloatStateOf(0f) }
    var characterX by remember { mutableFloatStateOf(300f) }

    // Gravity and speed settings
    val gravity = 0.4f
    var speed by remember { mutableFloatStateOf(0f) }

    // Jump settings
    var jumpSpeed by remember { mutableFloatStateOf(0f) }

    // To detect if character has landed
    val hasLanded = characterY + characterHeight.value >= screenHeight.value - groundHeight.value

    // LaunchedEffect to update the character's position with gravity
    LaunchedEffect(key1 = hasLanded) {
        while (!hasLanded) {
            speed += gravity // Increase speed over time
            characterY += speed // Update character's position

            // Check for collision with the ground
            if (characterY + characterHeight.value >= screenHeight.value - groundHeight.value) {
                characterY = screenHeight.value - groundHeight.value - characterHeight.value
                speed = 0f // Reset speed to prevent bouncing
            }

            // Delay to simulate frame updates
            delay(8L) // ~60 frames per second
        }
        itIsLanded()
    }

    // Function to move the character
    LaunchedEffect(key1 = destination) {
        while (true) {
            when (destination) {
                ScrollDestination.END -> { // Move forward
                    if (characterX + 5f <= endBorder) {
                        characterX += 5f
                    }
                    lastDestination = ScrollDestination.END
                }

                ScrollDestination.START -> { // Move backward
                    if (characterX - 5f >= startBorder) {
                        characterX -= 5f
                    }
                    lastDestination = ScrollDestination.START
                }

                ScrollDestination.NONE -> {} // Stay still
            }
            delay(16L) // Adjust delay for smooth movement
        }
    }

    // LaunchedEffect to handle jumping
    LaunchedEffect(key1 = jump) {
        if (jump && hasLanded) {
            jumpSpeed = -15f // Set initial jump speed (negative to move up)
            speed = 0f // Reset falling speed to avoid conflicts

            while (jumpSpeed < 0 || !hasLanded) {
                characterY += jumpSpeed
                jumpSpeed += gravity // Gravity affects jump speed

                // Check for collision with the ground
                if (characterY + characterHeight.value >= screenHeight.value - groundHeight.value) {
                    characterY = screenHeight.value - groundHeight.value - characterHeight.value
                    jumpSpeed = 0f // Stop jumping when landing
                    break
                }

                delay(8L) // Frame update delay
            }
        }
    }

    // Display the character at the current position
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(
                if (destination == ScrollDestination.NONE) R.drawable.four else R.drawable.eight
            )
            .decoderFactory(
                if (SDK_INT >= 28) {
                    ImageDecoderDecoder.Factory()
                } else {
                    GifDecoder.Factory()
                }
            )
            .build(),
        contentDescription = null,
        modifier = Modifier
            .graphicsLayer(
                translationY = characterY,
                translationX = characterX,
                scaleX = if (lastDestination == ScrollDestination.END) 1f else -1f // Flip image towards moving direction
            )
            .size(
                height = characterHeight,
                width = 80.dp
            ),
        contentScale = ContentScale.Fit
    )
}


