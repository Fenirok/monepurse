package com.aditya.monepurse.features.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aditya.monepurse.R
import com.aditya.monepurse.core.components.SequentialFadeInText
import com.aditya.monepurse.core.components.TypeWriterText
import com.aditya.monepurse.core.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit,
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var startIconAnimation by remember { mutableStateOf(false) }
    var startTitleAnimation by remember { mutableStateOf(false) }
    var startSubtitleAnimation by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }

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
            startTitleAnimation = true
        },
        label = "iconAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .windowInsetsPadding(WindowInsets.safeDrawing) // Equivalent to Flutter's SafeArea
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-64).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.monepurse_logo_another),
                contentDescription = "MonePurse Logo",
                modifier = Modifier
                    .size(150.dp)
                    .scale(iconScale)
                    .alpha(iconAlpha)
            )

            Box(modifier = Modifier.height(40.dp), contentAlignment = Alignment.Center) {
                if (startTitleAnimation) {
                    SequentialFadeInText(
                        text = "MonePurse.",
                        style = LocalTextStyle.current.copy(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-1).sp
                        ),
                        color = { index ->
                            if (index < 4) BrandDark else BrandBlue
                        },
                        onAnimationComplete = {
                            startSubtitleAnimation = true
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.height(20.dp), contentAlignment = Alignment.Center) {
                if (startSubtitleAnimation) {
                    TypeWriterText(
                        text = AnnotatedString("smart. simple. effortless"),
                        style = LocalTextStyle.current.copy(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                        color = TextGray,
                        delayPerChar = 40L,
                        onAnimationComplete = {
                            showButtons = true
                        }
                    )
                }
            }
        }

        // BOTTOM CONTENT: Pinned exactly to the bottom of the screen
        AnimatedVisibility(
            visible = showButtons,
            enter = fadeIn(animationSpec = tween(800)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Get Started",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SurfaceWhite
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Proceed",
                            tint = SurfaceWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.clickable { onLoginClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        color = TextGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Log In",
                        color = BrandBlue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                //Spacer(modifier = Modifier.height(32.dp)) // Adds breathing room at the very bottom edge above navigation bar
            }
        }
    }
}