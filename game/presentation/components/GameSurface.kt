package com.example.eazresume.game.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.eazresume.R

@Composable
fun GameSurface(
    modifier: Modifier = Modifier,
    listState: LazyListState,
) {
    LazyRow(
        state = listState,
        userScrollEnabled = false,
        modifier = modifier.fillMaxWidth()
    ) {
        items(20) {

            Column {
                Image(
                    painter = painterResource(id = R.drawable.surface),
                    contentDescription = "surface",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Inside
                )
            }
        }
    }
}