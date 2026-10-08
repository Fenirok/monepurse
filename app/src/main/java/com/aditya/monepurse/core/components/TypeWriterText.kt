package com.aditya.monepurse.core.components

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay

@Composable
fun TypeWriterText(
    text: AnnotatedString,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    delayPerChar: Long = 70L,
    onAnimationComplete: () -> Unit = {}
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(text) {
        for (i in 1..text.length) {
            delay(delayPerChar)
            currentIndex = i
        }
        onAnimationComplete()
    }

    Text(
        text = text.subSequence(0, currentIndex),
        style = style,
        color = color,
        modifier = modifier
    )
}