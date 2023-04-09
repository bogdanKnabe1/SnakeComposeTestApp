package com.test_task.snakecompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test_task.snakecompose.R
import com.test_task.snakecompose.domain.utils.PhoneMainPreview
import com.test_task.snakecompose.domain.utils.PhonePreview
import com.test_task.snakecompose.domain.utils.SetStatusBarTransparent
import com.test_task.snakecompose.presentation.theme.SnakeTheme
import com.test_task.snakecompose.presentation.theme.padding16dp
import com.test_task.snakecompose.presentation.theme.padding24dp
import com.test_task.snakecompose.presentation.ui_components.DisplayLarge
import com.test_task.snakecompose.presentation.viewmodels.AppViewModel
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel(),
    onGamePassReceived: () -> Unit,
    onWebViewReceived: (String) -> Unit
) {
    SetStatusBarTransparent(color = MaterialTheme.colorScheme.primaryContainer)

    val gamePassState by appViewModel.gamePassEnabled.collectAsStateWithLifecycle()
    val webLink by appViewModel.webLink.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
            .padding(padding16dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        StartScreenStateless(modifier)

        // Loading
        LaunchedEffect(true) {
            delay(2500) // wait loading for 2.5 seconds

            if (gamePassState) {
                onGamePassReceived.invoke()
            } else {
                onWebViewReceived.invoke(webLink)
            }
        }
    }
}

@Composable
private fun StartScreenStateless(modifier: Modifier = Modifier) {
    DisplayLarge(text = stringResource(id = R.string.app_name))

    Spacer(modifier = Modifier.height(padding24dp))

    Box {
        LinearProgressIndicator(
            modifier = modifier
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@PhonePreview
@Composable
fun StartScreenPreview() {
    SnakeTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .padding(padding16dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            StartScreenStateless()
        }
    }
}

@PhoneMainPreview
@Composable
fun StartScreenMainPreview() {
    SnakeTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .padding(padding16dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            StartScreenStateless()
        }
    }
}