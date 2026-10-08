package com.aditya.monepurse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.aditya.monepurse.features.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import com.aditya.monepurse.ui.theme.MonepurseTheme
// import com.aditya.monepurse.core.navigation.AppNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()
        setContent {
            MonepurseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Temporary Box to consume innerPadding and verify the app runs
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        SplashScreen(
                            onSplashComplete = {
                                // We will add navigation code here in the next step!
                            }
                        )
                    }
                }
            }
        }
    }
}