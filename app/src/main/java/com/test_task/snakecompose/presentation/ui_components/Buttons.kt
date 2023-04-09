package com.test_task.snakecompose.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.test_task.snakecompose.domain.utils.PhonePreview
import com.test_task.snakecompose.presentation.theme.SnakeTheme
import com.test_task.snakecompose.presentation.theme.padding4dp
import com.test_task.snakecompose.presentation.theme.size64dp

@Composable
fun AppButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) { Text(text = text) }
}

@Composable
fun AppIconButton(icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(size64dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(padding4dp)
            ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@PhonePreview
@Composable
fun AppButtonPreview() {
    SnakeTheme {
        AppButton(
            text = "Click",
            onClick = {})
    }
}

@PhonePreview
@Composable
fun AppIconButtonPreview() {
    SnakeTheme {
        AppIconButton(icon = Icons.Default.KeyboardArrowLeft, onClick = {})
    }
}