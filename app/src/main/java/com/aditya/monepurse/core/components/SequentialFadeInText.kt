package com.aditya.monepurse.core.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SequentialFadeInText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: (Int) -> Color = { Color.Unspecified }, // Allows changing color based on letter index
    delayPerChar: Long = 70L,
    animationDuration: Int = 400,
    onAnimationComplete: () -> Unit = {}
) {
    val charAlphas = remember { List(text.length) { Animatable(0f) } }

    LaunchedEffect(text) {
        charAlphas.forEachIndexed { index, animatable ->
            launch {
                delay(index * delayPerChar) // Stagger the start time of each letter
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = animationDuration) // Fade duration per letter
                )
            }
        }
        // Wait for all letters to finish fading in before triggering completion
        delay((text.length * delayPerChar) + animationDuration)
        onAnimationComplete()
    }

    Row(modifier = modifier) {
        text.forEachIndexed { index, char ->
            Text(
                text = char.toString(),
                style = style,
                color = color(index),
                modifier = Modifier.alpha(charAlphas[index].value) // Apply individual animated alpha
            )
        }
    }
}