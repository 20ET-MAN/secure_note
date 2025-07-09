package com.example.securenote.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.securenote.presentation.screen.auth.LoginScreen
import com.example.securenote.presentation.screen.firstlaunch.FirstLaunchScreen
import com.example.securenote.presentation.screen.home.HomeScreen
import com.example.securenote.presentation.screen.imagedetail.ImageDetailScreen
import com.example.securenote.presentation.screen.notedetail.NoteDetailScreen
import com.example.securenote.presentation.screen.setting.SettingScreen

@Composable
fun SecureAppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String,
) {
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
            HomeScreen(onGoToSetting = {
                navHostController.navigate(SecureNoteDestination.Setting.route)
            }, onGoToNoteDetail = { id ->
                navHostController.navigate(
                    SecureNoteDestination.NoteDetail.createRoute(
                        noteId = id,
                    )
                )
            })
        }

        composable(SecureNoteDestination.Setting.route) {
            SettingScreen(onGoToLicense = {
                navHostController.navigate(SecureNoteDestination.License.route)
            }, onBackPress = {
                navHostController.popBackStack()
            })
        }


        composable(
            route = SecureNoteDestination.NoteDetail.route,
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.LongType
                }
            ),
        ) { backStackEntry ->

            NoteDetailScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
                onNavigateToImageDetail = { imagePath, index ->
                    navHostController.navigate(
                        SecureNoteDestination.ImageDetail.createRoute(
                            imagePath, index
                        )
                    )
                }
            )
        }

        composable(
            route = SecureNoteDestination.ImageDetail.route,
            arguments = listOf(
                navArgument("imagePaths") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("index") {
                    type = NavType.IntType
                },
            ),
        ) { backStackEntry ->
            ImageDetailScreen(
                onBackPress = {
                    navHostController.popBackStack()
                },
            )
        }
    }
}