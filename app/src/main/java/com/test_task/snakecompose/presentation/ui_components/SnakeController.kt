package com.test_task.snakecompose.presentation.ui_components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.test_task.snakecompose.domain.game.SnakeDirection
import com.test_task.snakecompose.presentation.theme.*

@Composable
fun SnakeController(onDirectionChange: (Int) -> Unit) {
    val buttonSize = Modifier.size(size64dp)
    val currentDirection = remember { mutableStateOf(SnakeDirection.Right) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppIconButton(modifier = Modifier.padding(padding8dp), icon = Icons.Default.KeyboardArrowUp) {
            if (currentDirection.value != SnakeDirection.Down) {
                onDirectionChange.invoke(SnakeDirection.Up)
                currentDirection.value = SnakeDirection.Up
            }
        }

        Row() {
            AppIconButton(icon = Icons.Default.KeyboardArrowLeft) {
                if (currentDirection.value != SnakeDirection.Right) {
                    onDirectionChange.invoke(SnakeDirection.Left)
                    currentDirection.value = SnakeDirection.Left
                }
            }

            Spacer(modifier = buttonSize)

            AppIconButton(icon = Icons.Default.KeyboardArrowRight) {
                if (currentDirection.value != SnakeDirection.Left) {
                    onDirectionChange.invoke(SnakeDirection.Right)
                    currentDirection.value = SnakeDirection.Right
                }
            }
        }

        AppIconButton(modifier = Modifier.padding(padding8dp), icon = Icons.Default.KeyboardArrowDown) {
            if (currentDirection.value != SnakeDirection.Up) {
                onDirectionChange.invoke(SnakeDirection.Down)
                currentDirection.value = SnakeDirection.Down
            }
        }
    }
}

@Preview
@Composable
fun SnakeControllerPreview() {
    SnakeTheme {
        SnakeController(onDirectionChange = {})
    }
}