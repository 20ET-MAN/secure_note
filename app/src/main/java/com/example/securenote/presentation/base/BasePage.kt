package com.example.securenote.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.securenote.presentation.component.InitialError
import com.example.securenote.presentation.dialog.CommonErrorDialog

@Composable
fun <VM : BaseViewModel> BasePage(
    viewModel: VM,
    content: @Composable (VM) -> Unit
) {
    val pageStatus by viewModel.pageStatus.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (pageStatus) {
                PageStatus.INITIAL_ERROR -> {
                    InitialError(
                        onRetryClick = { viewModel.retry() }
                    )
                }

                PageStatus.INITIAL -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                }

                PageStatus.LOADED -> {
                    content(viewModel)
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center),
                            )
                        }
                    }
                }
            }
            val isShowErrorDialog = error != null && pageStatus == PageStatus.LOADED
            CommonErrorDialog(
                show = isShowErrorDialog,
                onCancel = { viewModel.clearError() },
                onSummit = { viewModel.retry() }
            )
        }
    }
}

enum class PageStatus {
    INITIAL, LOADED, INITIAL_ERROR
}
