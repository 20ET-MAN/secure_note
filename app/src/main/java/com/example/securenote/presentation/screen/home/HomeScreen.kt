package com.example.securenote.presentation.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.R
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar
import com.example.securenote.presentation.screen.home.components.AnalyticsPage
import com.example.securenote.presentation.screen.home.components.HomeTabBar
import com.example.securenote.presentation.screen.home.components.NotePage
import com.example.securenote.presentation.screen.home.components.TabItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onGoToSetting: () -> Unit,
    onGoToNoteDetail: (id: Long) -> Unit,
) {

    var homeUIState = viewModel.homeUiState.collectAsState()

    var tabs = mutableListOf(
        TabItem(stringResource(R.string.home_tabs_note_tit), painterResource(R.drawable.ic_note)),
        TabItem(
            stringResource(R.string.home_tabs_analytics_tit),
            painterResource(R.drawable.ic_chart)
        )
    )

    val pagerState = rememberPagerState(pageCount = {
        tabs.size
    })

    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.onTabChange(page)
        }
    }

    BasePage(viewModel = viewModel) {
        Scaffold(
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .clickable {
                            onGoToNoteDetail(-1)
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
                    isShowNavigationBtn = false,
                    actionButton = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
                )
                HomeTabBar(
                    tabs = tabs,
                    selectedTabIndex = homeUIState.value.selectedTab,
                    noteCount = homeUIState.value.notes.size,
                    onTabSelected = {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    },
                )

                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> NotePage(
                            homeUIState.value.notes,
                            onNoteEdits = { id ->
                                onGoToNoteDetail(id)
                            },
                        )

                        1 -> AnalyticsPage(
                            lineData = homeUIState.value.analyticLineChartData,
                            onLineChartChangeRange = {
                                viewModel.loadLineChartData(it)
                            },
                            currentDateRange = homeUIState.value.currentDateRange,
                            pieData = homeUIState.value.pieChartData
                        )
                    }
                }
            }
        }
    }
}
