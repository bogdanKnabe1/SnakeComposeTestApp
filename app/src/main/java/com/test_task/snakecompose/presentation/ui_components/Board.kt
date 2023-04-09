package com.test_task.snakecompose.presentation.ui_components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.test_task.snakecompose.data.model.State
import com.test_task.snakecompose.domain.game.SnakeDirection
import com.test_task.snakecompose.domain.game.SnakeGameEngine
import com.test_task.snakecompose.domain.utils.PhonePreview
import com.test_task.snakecompose.presentation.theme.*


@Composable
fun Board(state: State, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier.padding(padding16dp)) {

        val tileSize = maxWidth / SnakeGameEngine.GAME_BOARD_SIZE

        Box(
            modifier
                .size(maxWidth)
                .border(padding2dp, md_theme_light_primary)
        )

        Food(state, tileSize, modifier)

        SnakeBody(state, tileSize, modifier)
    }
}

@Composable
fun Food(state: State, tileSize: Dp, modifier: Modifier = Modifier) {
    // Food
    Box(
        modifier
            .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
            .size(tileSize)
            .background(foodColor, CircleShape)
    )
}

@Composable
fun SnakeBody(state: State, tileSize: Dp, modifier: Modifier = Modifier) {
    // Snake body
    state.snake.forEachIndexed { index, coordinates ->
        Box(
            modifier = modifier
                .offset(
                    x = tileSize * coordinates.first, y = tileSize * coordinates.second
                )
                .size(tileSize + padding2dp)
                .background(
                    snakeColor, RoundedCornerShape(padding4dp)
                )
        ) {
            /*if (index == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SnakeEye(tileSize = tileSize)
                    SnakeEye(tileSize = tileSize)
                }
            }*/
        }
    }
}

@Composable
fun SnakeEye(tileSize: Dp, modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val alpha by transition.animateFloat(
        initialValue = 1f, targetValue = 0f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .size(tileSize / 2)
            .padding(top = padding2dp, end = padding2dp)
            .background(Color.White.copy(alpha = alpha), CircleShape)
    )
}

@PhonePreview
@Composable
fun BoardPreview() {
    SnakeTheme {
        Board(
            state = State(
                food = Pair(10, 10),
                listOf(Pair(2, 2), Pair(1, 2)),
                currentDirection = SnakeDirection.Right
            ), modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        )
    }
}