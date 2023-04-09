package com.test_task.snakecompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.test_task.snakecompose.R
import com.test_task.snakecompose.domain.utils.PhonePreview
import com.test_task.snakecompose.domain.utils.SetStatusBarTransparent
import com.test_task.snakecompose.domain.utils.launchActivity
import com.test_task.snakecompose.presentation.activities.SnakeGameActivity
import com.test_task.snakecompose.presentation.theme.*
import com.test_task.snakecompose.presentation.ui_components.AppButton
import com.test_task.snakecompose.presentation.ui_components.DisplayLarge

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    onHighScoresClick: () -> Unit,
    onSetNameClick: () -> Unit,
) {
    SetStatusBarTransparent(color = MaterialTheme.colorScheme.background)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding16dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current

        DisplayLarge(text = stringResource(id = R.string.game_name))

        AppButton(
            modifier = modifier
                .width(width264dp)
                .padding(top = padding64dp),
            text = stringResource(R.string.new_game)
        ) { context.launchActivity<SnakeGameActivity>() }

        Spacer(modifier = modifier.padding(padding8dp))

        AppButton(
            modifier = modifier.width(width264dp),
            text = stringResource(id = R.string.high_score)
        ) {
            onHighScoresClick.invoke()
        }

        Spacer(modifier = modifier.padding(padding8dp))

        AppButton(modifier = modifier.width(width264dp), text = stringResource(R.string.set_name)) {
            onSetNameClick.invoke()
        }
    }
}

@PhonePreview
@Composable
fun MenuScreenPreview() {
    SnakeTheme {
        MenuScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onHighScoresClick = {},
            onSetNameClick = {}
        )
    }
}