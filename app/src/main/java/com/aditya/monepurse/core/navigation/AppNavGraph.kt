package com.aditya.monepurse.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aditya.monepurse.features.splash.SplashScreen
import com.aditya.monepurse.features.auth.CreateAccountScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.SPLASH) {

        // 1. Splash Screen
        composable(NavRoutes.SPLASH) {
            SplashScreen(
                onSplashComplete = { /* Ignored if you removed this */ },
                onGetStartedClick = {
                    // Navigate to Create Account when "Get Started" is clicked
                    navController.navigate(NavRoutes.CREATE_ACCOUNT)
                },
                onLoginClick = {


                }
            )
        }

        // 2. Create Account Screen
        composable(NavRoutes.CREATE_ACCOUNT) {
            CreateAccountScreen(
                onBackClick = { navController.popBackStack() },
                onContinueClick = { },
                onLoginClick = {  }
            )
        }
    }
}