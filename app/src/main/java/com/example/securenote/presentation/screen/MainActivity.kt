package com.example.securenote.presentation.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
        val isFirstLaunch = mainViewModel.isFirstLaunch.value
        enableEdgeToEdge()
        setContent {
            SecureNoteTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    SecureAppNavHost(
                        startDestination = if (isFirstLaunch) SecureNoteDestination.FirstLaunch.route else SecureNoteDestination.Login.route,
                    )
                }
            }
        }
    }
}