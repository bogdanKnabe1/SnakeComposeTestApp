package com.test_task.snakecompose.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.test_task.snakecompose.domain.utils.PhonePreview
import com.test_task.snakecompose.presentation.theme.SnakeTheme

@Composable
fun DisplayLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.displayLarge,
        textAlign = textAlign
    )
}

@Composable
fun TitleLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleLarge,
        textAlign = textAlign
    )
}

@Composable
fun BodyLarge(text: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Start) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = textAlign
    )
}

@PhonePreview
@Composable
fun DisplayLargePreview() {
    SnakeTheme {
        DisplayLarge(
            text = "Snake!",
            Modifier.background(color = MaterialTheme.colorScheme.background)
        )
    }
}

@PhonePreview
@Composable
fun TitleLargePreview() {
    SnakeTheme {
        TitleLarge(
            text = "Title with text",
            Modifier.background(color = MaterialTheme.colorScheme.background)
        )
    }
}

@PhonePreview
@Composable
fun BodyLargePreview() {
    SnakeTheme {
        BodyLarge(
            text = "Default text",
            Modifier.background(color = MaterialTheme.colorScheme.background)
        )
    }
}