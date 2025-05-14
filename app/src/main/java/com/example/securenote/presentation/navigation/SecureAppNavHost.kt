package com.example.securenote.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securenote.presentation.screen.auth.LoginScreen
import com.example.securenote.presentation.screen.firstlaunch.FirstLaunchScreen
import com.example.securenote.presentation.screen.home.HomeScreen

@Composable
fun SecureAppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    //val appNavigator = remember(navHostController) { SecureAppNavigator(navHostController) }
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {

        composable(SecureNoteDestination.FirstLaunch.route) {
            FirstLaunchScreen(
                onGoToLogin = {
                    navHostController.navigate(SecureNoteDestination.Login.route) {
                        popUpTo(SecureNoteDestination.FirstLaunch.route) { inclusive = true }
                    }
                },
            )
        }
        composable(SecureNoteDestination.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navHostController.navigate(SecureNoteDestination.Home.route) {
                        popUpTo(SecureNoteDestination.Login.route) { inclusive = true }
                    }
                },
            )
        }
        composable(SecureNoteDestination.Home.route) {
            HomeScreen()
        }
    }
}