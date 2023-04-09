package com.test_task.snakecompose.domain.game

import androidx.compose.runtime.mutableStateOf
import com.test_task.snakecompose.data.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*

class SnakeGameEngine(
    private val scope: CoroutineScope,
    private val onGameEnded: () -> Unit,
    private val onFoodEaten: () -> Unit
) {
    private val mutex = Mutex()
    private val mutableState =
        MutableStateFlow(
            State(
                food = Pair(10, 10),
                snake = listOf(Pair(2, 2)),
                currentDirection = SnakeDirection.Right
            )
        )
    val state: Flow<State> = mutableState
    val currentDirection = mutableStateOf(SnakeDirection.Right)

    var move = Pair(1, 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    fun reset() {
        mutableState.update {
            it.copy(
                food = Pair(10, 10),
                snake = listOf(Pair(2, 2)),
                currentDirection = SnakeDirection.Right
            )
        }
        currentDirection.value = SnakeDirection.Right
        move = Pair(1, 0)
    }

    /**
     * Main loop of Snake game.
     * 1) Moving the snake
     * 2) Updating snake directions
     * 3) Checking collisions
     * 4) working asynchronously in coroutines an updating in sync with mutex object.
     */
    init {
        scope.launch {
            var snakeLength = 2
            while (true) {
                delay(150)
                mutableState.update {
                    val hasReachedLeftEnd =
                        it.snake.first().first == 0 && it.currentDirection == SnakeDirection.Left
                    val hasReachedTopEnd =
                        it.snake.first().second == 0 && it.currentDirection == SnakeDirection.Up
                    val hasReachedRightEnd =
                        it.snake.first().first == GAME_BOARD_SIZE - 1 && it.currentDirection == SnakeDirection.Right
                    val hasReachedBottomEnd =
                        it.snake.first().second == GAME_BOARD_SIZE - 1 && it.currentDirection == SnakeDirection.Down
                    if (hasReachedLeftEnd || hasReachedTopEnd || hasReachedRightEnd || hasReachedBottomEnd) {
                        snakeLength = 2
                        onGameEnded.invoke()
                    }
                    if (move.first == 0 && move.second == -1) {
                        currentDirection.value = SnakeDirection.Up
                    } else if (move.first == -1 && move.second == 0) {
                        currentDirection.value = SnakeDirection.Left
                    } else if (move.first == 1 && move.second == 0) {
                        currentDirection.value = SnakeDirection.Right
                    } else if (move.first == 0 && move.second == 1) {
                        currentDirection.value = SnakeDirection.Down
                    }
                    val newPosition = it.snake.first().let { poz ->
                        mutex.withLock {
                            Pair(
                                (poz.first + move.first + GAME_BOARD_SIZE) % GAME_BOARD_SIZE,
                                (poz.second + move.second + GAME_BOARD_SIZE) % GAME_BOARD_SIZE
                            )
                        }
                    }
                    if (newPosition == it.food) {
                        onFoodEaten.invoke()
                        snakeLength++
                    }

                    if (it.snake.contains(newPosition)) {
                        snakeLength = 2
                        onGameEnded.invoke()
                    }

                    it.copy(
                        food = if (newPosition == it.food) Pair(
                            Random().nextInt(GAME_BOARD_SIZE),
                            Random().nextInt(GAME_BOARD_SIZE)
                        ) else it.food,
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1),
                        currentDirection = currentDirection.value,
                    )
                }
            }
        }
    }

    companion object {
        const val GAME_BOARD_SIZE = 32
    }
}
