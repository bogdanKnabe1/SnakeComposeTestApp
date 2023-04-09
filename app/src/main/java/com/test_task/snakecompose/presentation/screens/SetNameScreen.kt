package com.test_task.snakecompose.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test_task.snakecompose.R
import com.test_task.snakecompose.data.cache.SnakeGameCache
import com.test_task.snakecompose.presentation.theme.*
import com.test_task.snakecompose.presentation.ui_components.AppBar
import com.test_task.snakecompose.presentation.ui_components.AppButton
import com.test_task.snakecompose.presentation.ui_components.DisplayLarge
import com.test_task.snakecompose.presentation.ui_components.TitleLarge
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetNameScreen(onBackClick: () -> Unit, onSaveClick: () -> Unit) {
    val dataStore = SnakeGameCache(LocalContext.current)
    var text by remember { mutableStateOf(TextFieldValue("")) }

    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    AppBar(
        title = stringResource(R.string.title_set_name),
        onBackClicked = { onBackClick.invoke() }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = padding16dp,
                    start = padding16dp,
                    end = padding16dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            DisplayLarge(
                modifier = Modifier.padding(
                    top = padding64dp,
                    bottom = padding16dp,
                    start = padding16dp,
                    end = padding16dp
                ),
                text = stringResource(id = R.string.player_name),
                textAlign = TextAlign.Center
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            TextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.secondary,
                ),
                singleLine = true,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(horizontal = padding64dp)
                    .border(width = padding2dp, color = MaterialTheme.colorScheme.tertiary)
            )

            AppButton(
                text = stringResource(R.string.save), modifier = Modifier
                    .width(width264dp)
                    .padding(padding16dp)
            ) {
                scope.launch {
                    dataStore.savePlayerName(text.text.trim())
                    Toast.makeText(context, R.string.player_name_updated, Toast.LENGTH_SHORT).show()
                    onSaveClick.invoke()
                }
            }
        }
    }
}

@Preview
@Composable
fun SetNameScreenPreview() {
    SnakeTheme {
        SetNameScreen(onBackClick = { }, onSaveClick = {})
    }
}