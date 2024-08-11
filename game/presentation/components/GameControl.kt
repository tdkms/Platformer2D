package com.example.eazresume.game.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eazresume.R

@Composable
fun GameControl(
    forwardSource: MutableInteractionSource,
    backwardSource: MutableInteractionSource,
    onJump: () -> Unit
) {
    val btnSize = 400.dp
    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(40.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                IconButton(
                    onClick = { /* Do nothing on click */ },
                    interactionSource = backwardSource
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.move_backward_button),
                        contentDescription = "backward",
                        modifier = Modifier.size(btnSize),
                        contentScale = ContentScale.FillBounds
                    )
                }
                IconButton(
                    onClick = { /* Do nothing on click */ },
                    interactionSource = forwardSource
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.move_forward_button),
                        contentDescription = "forward",
                        modifier = Modifier.size(btnSize),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            IconButton(onClick = onJump) {
                Image(
                    painter = painterResource(id = R.drawable.jump_button),
                    contentDescription = "jump",
                    modifier = Modifier.size(btnSize),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}