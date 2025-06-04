package com.example.securenote.presentation.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.securenote.presentation.navigation.SecureAppNavHost
import com.example.securenote.presentation.navigation.SecureNoteDestination
import com.example.securenote.ui.theme.SecureNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val isFirstLaunch = mainViewModel.isFirstLaunch.collectAsState()
            val isDarkMode = mainViewModel.isDarkMode.collectAsState()

            SecureNoteTheme(darkTheme = isDarkMode.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SecureAppNavHost(
                        startDestination = if (isFirstLaunch.value) SecureNoteDestination.FirstLaunch.route else SecureNoteDestination.Login.route,
                    )
                }
            }
        }
    }
}