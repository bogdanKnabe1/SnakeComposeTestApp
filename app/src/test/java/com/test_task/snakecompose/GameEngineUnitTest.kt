package com.test_task.snakecompose

import com.test_task.snakecompose.domain.game.SnakeDirection
import com.test_task.snakecompose.domain.game.SnakeGameEngine
import com.test_task.snakecompose.domain.game.SnakeGameEngine.Companion.GAME_BOARD_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GameEngineUnitTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private var gameEnded = false
    private val gameEndedCallback: () -> Unit = {
        gameEnded = true
    }

    var foodEaten = false
    private val foodEatenCallback: () -> Unit = {
        foodEaten = true
    }


    @Test
    fun initialStateIsCorrect() = runTest {
        val gameEngine = SnakeGameEngine(testScope, {}, {})
        val state = gameEngine.state.first()
        assertEquals(state.snake, listOf(Pair(2, 2)))
        assertEquals(state.food, Pair(10, 10))
        assertEquals(state.currentDirection, SnakeDirection.Right)
    }

    @Test
    fun resetGameSetsInitialState() = runTest {
        val gameEngine = SnakeGameEngine(testScope, {}, {})
        gameEngine.reset()
        val state = gameEngine.state.first()
        assertEquals(state.snake, listOf(Pair(2, 2)))
        assertEquals(state.food, Pair(10, 10))
        assertEquals(state.currentDirection, SnakeDirection.Right)
    }

    @Test
    fun updateNewPosition() = runTest {
        val gameEngine = SnakeGameEngine(this, {}, {})
        gameEngine.move = Pair(1, 0) // move right
        delay(250)

        val state = gameEngine.state.first()
        val newPosition = state.snake.first().let { poz ->
            Pair(
                (poz.first + gameEngine.move.first + GAME_BOARD_SIZE) % GAME_BOARD_SIZE,
                (poz.second + gameEngine.move.second + GAME_BOARD_SIZE) % GAME_BOARD_SIZE
            )
        }
        assertEquals(newPosition, state.snake)
    }
}