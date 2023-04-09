package com.test_task.snakecompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.test_task.snakecompose.R
import com.test_task.snakecompose.data.cache.SnakeGameCache
import com.test_task.snakecompose.data.model.HighScore
import com.test_task.snakecompose.domain.utils.PhonePreview
import com.test_task.snakecompose.domain.utils.TOP_20
import com.test_task.snakecompose.presentation.theme.SnakeTheme
import com.test_task.snakecompose.presentation.theme.padding16dp
import com.test_task.snakecompose.presentation.theme.padding2dp
import com.test_task.snakecompose.presentation.theme.padding8dp
import com.test_task.snakecompose.presentation.ui_components.AppBar
import com.test_task.snakecompose.presentation.ui_components.TitleLarge

@Composable
fun HighScoreScreen(onBackClick: () -> Unit) {
    val dataStore = SnakeGameCache(LocalContext.current)
    val highScores =
        dataStore.getHighScores.collectAsState(initial = listOf()).value.sortedByDescending { it.score }
            .take(TOP_20)
    AppBar(
        title = stringResource(R.string.high_score),
        onBackClicked = { onBackClick.invoke() }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = padding16dp,
                    start = padding16dp,
                    end = padding16dp
                )
                .border(width = padding2dp, color = MaterialTheme.colorScheme.onBackground),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(padding16dp)
            ) {
                TitleLarge(
                    text = stringResource(R.string.player_name),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                TitleLarge(
                    text = stringResource(R.string.score),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = highScores) { userHighScore ->
                    HighScoreItem(userHighScore)
                }
            }
        }
    }

}

@Composable
private fun HighScoreItem(highScore: HighScore, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(padding8dp)
    ) {
        TitleLarge(
            text = highScore.playerName,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        TitleLarge(
            text = highScore.score.toString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@PhonePreview
@Composable
fun HighScoreItemPreview() {
    SnakeTheme {
        HighScoreItem(
            highScore = HighScore(playerName = "Player one", score = 999),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@PhonePreview
@Composable
fun HighScoresScreenPreview() {
    SnakeTheme {
        HighScoreScreen(onBackClick = {})
    }
}