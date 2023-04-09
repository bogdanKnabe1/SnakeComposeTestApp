package com.test_task.snakecompose.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.test_task.snakecompose.domain.game.SnakeDirection
import com.test_task.snakecompose.R
import com.test_task.snakecompose.domain.game.SnakeGameEngine
import com.test_task.snakecompose.presentation.activities.SnakeGameActivity
import com.test_task.snakecompose.presentation.ui_components.AppBar
import com.test_task.snakecompose.presentation.ui_components.Board
import com.test_task.snakecompose.presentation.ui_components.SnakeController

@Composable
fun GameScreen(snakeGameEngine: SnakeGameEngine, score: Int) {
    val state = snakeGameEngine.state.collectAsState(initial = null)
    val activity = LocalContext.current as SnakeGameActivity
    AppBar(
        title = stringResource(id = R.string.your_score, score),
        onBackClicked = { activity.finish() }) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.value?.let { Board(it) }

            SnakeController {
                when (it) {
                    SnakeDirection.Up -> snakeGameEngine.move = Pair(0, -1)
                    SnakeDirection.Left -> snakeGameEngine.move = Pair(-1, 0)
                    SnakeDirection.Right -> snakeGameEngine.move = Pair(1, 0)
                    SnakeDirection.Down -> snakeGameEngine.move = Pair(0, 1)
                }
            }
        }
    }
}