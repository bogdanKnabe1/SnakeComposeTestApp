package com.test_task.snakecompose.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.test_task.snakecompose.R

private val appFontFamily = FontFamily(
    fonts = listOf(
        Font(resId = R.font.raleway_snake_bold),
        Font(resId = R.font.raleway_snake_medium),
        Font(resId = R.font.raleway_snake_regular),
        Font(resId = R.font.raleway_snake_light)
    )
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),
)