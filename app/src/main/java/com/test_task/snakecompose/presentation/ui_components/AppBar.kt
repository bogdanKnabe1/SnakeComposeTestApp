package com.test_task.snakecompose.presentation.ui_components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.test_task.snakecompose.domain.utils.PhonePreview
import com.test_task.snakecompose.presentation.theme.SnakeTheme
import com.test_task.snakecompose.presentation.theme.padding16dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    content: @Composable (padding: PaddingValues) -> Unit
) {
    Scaffold(topBar = {
        Toolbar(title, onBackClicked = onBackClicked)
    }) { contentPadding -> content.invoke(contentPadding) }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Toolbar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        title = { TitleLarge(text = title) },
        modifier = Modifier.padding(horizontal = padding16dp),
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Back arrow icon"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@PhonePreview
@Composable
fun ToolbarPreview() {
    SnakeTheme {
        Toolbar(title = "Snake App", onBackClicked = {})
    }
}

@PhonePreview
@Composable
fun SnakeAppBarPreview() {
    SnakeTheme {
        AppBar(title = "Snake App", onBackClicked = {}, content = {})
    }
}