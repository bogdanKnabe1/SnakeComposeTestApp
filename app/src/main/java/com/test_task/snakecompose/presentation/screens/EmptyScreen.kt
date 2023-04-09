package com.test_task.snakecompose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.test_task.snakecompose.R
import com.test_task.snakecompose.domain.utils.PhonePreview
import com.test_task.snakecompose.domain.utils.SetStatusBarTransparent
import com.test_task.snakecompose.presentation.theme.SnakeTheme
import com.test_task.snakecompose.presentation.theme.padding16dp
import com.test_task.snakecompose.presentation.ui_components.AppButton
import com.test_task.snakecompose.presentation.ui_components.DisplayLarge

@Composable
fun EmptyScreen(modifier: Modifier = Modifier, onRetryClick: () -> Unit) {

    SetStatusBarTransparent(color = MaterialTheme.colorScheme.background)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DisplayLarge(text = "Link is empty", textAlign = TextAlign.Center)

            Spacer(modifier = modifier.padding(padding16dp))

            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                // Add your component here
                AppButton(text = stringResource(id = R.string.try_again)) {
                    onRetryClick.invoke()
                }
            }
        }
    }
}

@PhonePreview
@Composable
fun EmptyScreenPreview() {
    SnakeTheme {
        EmptyScreen(onRetryClick = {})
    }
}