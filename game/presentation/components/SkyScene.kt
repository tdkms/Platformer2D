package com.example.eazresume.game.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.eazresume.DayPart
import com.example.eazresume.R
import com.example.eazresume.game.presentation.util.ScrollDestination
import com.example.eazresume.game.presentation.util.getRandomFloatInRange
import com.example.eazresume.game.presentation.util.getRandomIntInRange
import com.example.eazresume.game.presentation.util.randomObjectSize
import com.example.eazresume.getSkyColors
import kotlin.random.Random

@Composable
fun SkyScene(
    modifier: Modifier = Modifier,
    forwardSource: MutableInteractionSource,
    backwardSource: MutableInteractionSource,
    listState: LazyListState,
    currentDayPart: DayPart,
    partDuration: Int,
    count: Int = 20,
    destination: ScrollDestination
) {
    var jump by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = getSkyColors(
                        currentDayPart = currentDayPart,
                        partDuration = partDuration
                    )
                )
            )
    ) {
        // Sky with clouds
        LazyRow(
            state = listState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize()
        ) {
            items(count) { index ->
                val randomFloat = Random.getRandomFloatInRange(0.0f, 0.2f)
                val randomDp = Random.getRandomIntInRange(200, 600)
                Column {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight(randomFloat)
                            .width(randomDp.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.cloud),
                        contentDescription = "Cloud",
                        modifier = Modifier.randomObjectSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

            }
        }

        // Falling Character
        Character(
            destination = destination,
            jump = jump,
            itIsLanded = { jump = !jump }
        )

        GameControl(
            forwardSource = forwardSource,
            backwardSource = backwardSource,
        ) {
            // When jump button is pressed
            jump = true
        }
    }
}
