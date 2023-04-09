package com.test_task.snakecompose.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.test_task.snakecompose.data.model.HighScore
import com.test_task.snakecompose.R
import com.test_task.snakecompose.data.cache.SnakeGameCache
import com.test_task.snakecompose.domain.utils.TOP_20
import com.test_task.snakecompose.domain.game.SnakeGameEngine
import com.test_task.snakecompose.presentation.screens.EndScreen
import com.test_task.snakecompose.presentation.screens.GameScreen
import com.test_task.snakecompose.presentation.theme.SnakeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnakeGameActivity : ComponentActivity() {
    private lateinit var dataStore: SnakeGameCache

    private lateinit var coroutineScope: CoroutineScope
    private var playerScore = mutableStateOf(0)
    private lateinit var highScores: List<HighScore>
    private val isPlaying = mutableStateOf(true)

    private lateinit var playerName: String

    private var snakeGameEngine = SnakeGameEngine(
        scope = lifecycleScope,
        onGameEnded = {
            if (isPlaying.value) {
                isPlaying.value = false
                coroutineScope.launch { dataStore.saveHighScore(highScores) }
            }
        },
        onFoodEaten = { playerScore.value++ }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnakeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SnakeGame()
                }
            }
        }
    }

    @Composable
    fun SnakeGame() {
        coroutineScope = rememberCoroutineScope()
        dataStore = SnakeGameCache(applicationContext)

        playerName = dataStore.getPlayerName
            .collectAsState(initial = stringResource(id = R.string.default_player_name)).value

        highScores = dataStore.getHighScores
            .collectAsState(initial = listOf()).value.plus(
                HighScore(
                    playerName,
                    playerScore.value
                )
            ).sortedByDescending { it.score }.take(TOP_20)

        Column {
            if (isPlaying.value) {
                GameScreen(snakeGameEngine, playerScore.value)
            } else {
                EndScreen(playerScore.value) {
                    playerScore.value = 0
                    snakeGameEngine.reset()
                    isPlaying.value = true
                }
            }
        }
    }
}