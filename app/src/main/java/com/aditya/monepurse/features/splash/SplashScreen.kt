package com.aditya.monepurse.features.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aditya.monepurse.R
import com.aditya.monepurse.core.components.TypeWriterText
import com.aditya.monepurse.core.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit // Called when all animations are done
) {
    // Animation Sequence States
    var startIconAnimation by remember { mutableStateOf(false) }
    var startTitleAnimation by remember { mutableStateOf(false) }
    var startSubtitleAnimation by remember { mutableStateOf(false) }

    // 1. Start icon animation immediately
    LaunchedEffect(Unit) {
        startIconAnimation = true
    }

    val iconScale by animateFloatAsState(
        targetValue = if (startIconAnimation) 1f else 0.5f,
        animationSpec = tween(durationMillis = 800),
        label = "iconScale"
    )

    val iconAlpha by animateFloatAsState(
        targetValue = if (startIconAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        finishedListener = {
            // 2. Start title typing when icon finishes
            startTitleAnimation = true
        },
        label = "iconAlpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Centers the logo and text perfectly
    ) {
        // Phase 1: Custom Logo Animation
        Image(
            painter = painterResource(id = R.drawable.monepurseicon),
            contentDescription = "MonePurse Logo",
            modifier = Modifier
                .size(140.dp)
                .scale(iconScale)
                .alpha(iconAlpha)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Phase 2: Title Typing Animation
        Box(modifier = Modifier.height(40.dp), contentAlignment = Alignment.Center) {
            if (startTitleAnimation) {
                val titleString = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = BrandDark, fontWeight = FontWeight.ExtraBold)) { append("Mone") }
                    withStyle(style = SpanStyle(color = BrandBlue, fontWeight = FontWeight.ExtraBold)) { append("Purse.") }
                }

                TypeWriterText(
                    text = titleString,
                    style = LocalTextStyle.current.copy(fontSize = 36.sp, letterSpacing = (-1).sp),
                    onAnimationComplete = {
                        // 3. Start subtitle when title finishes
                        startSubtitleAnimation = true
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Phase 3: Subtitle Typing Animation
        Box(modifier = Modifier.height(20.dp), contentAlignment = Alignment.Center) {
            if (startSubtitleAnimation) {
                // We use a state to ensure the delay coroutine only launches once
                var isFinished by remember { mutableStateOf(false) }

                LaunchedEffect(isFinished) {
                    if (isFinished) {
                        delay(1000) // Pause for 1 second so the user can read it
                        onSplashComplete() // Trigger navigation to the next screen
                    }
                }

                TypeWriterText(
                    text = AnnotatedString("smart. simple. effortless"),
                    style = LocalTextStyle.current.copy(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                    color = TextGray,
                    delayPerChar = 40L,
                    onAnimationComplete = {
                        isFinished = true
                    }
                )
            }
        }
    }
}