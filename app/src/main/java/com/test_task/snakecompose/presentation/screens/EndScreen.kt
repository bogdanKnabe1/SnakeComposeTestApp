package com.test_task.snakecompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.test_task.snakecompose.R
import com.test_task.snakecompose.presentation.activities.SnakeGameActivity
import com.test_task.snakecompose.presentation.theme.SnakeTheme
import com.test_task.snakecompose.presentation.theme.padding16dp
import com.test_task.snakecompose.presentation.theme.padding8dp
import com.test_task.snakecompose.presentation.ui_components.AppBar
import com.test_task.snakecompose.presentation.ui_components.AppButton
import com.test_task.snakecompose.presentation.ui_components.DisplayLarge
import com.test_task.snakecompose.presentation.ui_components.TitleLarge

@Composable
fun EndScreen(score: Int, onTryAgain: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(padding16dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        DisplayLarge(
            text = stringResource(R.string.game_over),
            modifier = Modifier.padding(top = padding8dp),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        TitleLarge(
            modifier = Modifier.padding(padding16dp),
            text = stringResource(id = R.string.your_score, score),
        )

        AppButton(text = stringResource(R.string.try_again)) { onTryAgain.invoke() }
    }
}

@Preview
@Composable
fun EndScreenPreview() {
    SnakeTheme {
        EndScreen(score = 300, onTryAgain = {})
    }
}