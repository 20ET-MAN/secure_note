package com.example.securenote.presentation.screen.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.R
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar
import com.example.securenote.presentation.screen.components.AppTabBar
import com.example.securenote.presentation.screen.components.TabItem
import com.example.securenote.presentation.screen.home.components.AnalyticsPage
import com.example.securenote.presentation.screen.home.components.NotePage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(onGoToSetting: () -> Unit, onGoToNoteDetail: (String?) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()

    var homeUIState = viewModel.homeUiState.collectAsState()

    val tabs = listOf(
        TabItem("Notes", painterResource(R.drawable.ic_note), 6),
        TabItem("Analytics", painterResource(R.drawable.ic_chart))
    )

    BasePage(viewModel = viewModel) {
        Scaffold(
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .clickable {
                            onGoToNoteDetail(null)
                        }
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)

                ) {
                    Icon(imageVector = Icons.Default.Create, contentDescription = null)
                }
            },
        ) { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppAppBar(
                    title = stringResource(R.string.app_name),
                    onActionBtnClick = onGoToSetting,
                    isShowActionBtn = true,
                    isShowNavigationBtn = false
                )
                AppTabBar(
                    tabs = tabs,
                    selectedTabIndex = homeUIState.value.selectedTab,
                    onTabSelected = { viewModel.onTabChange(it) },
                )

                AnimatedContent(
                    targetState = homeUIState.value.selectedTab,
                    label = "TabContent"
                ) { tab ->
                    when (tab) {
                        0 -> NotePage(homeUIState.value.notes, onNoteEdits = { id ->
                            onGoToNoteDetail(id)
                        })

                        1 -> AnalyticsPage()
                    }
                }
            }
        }
    }
}
