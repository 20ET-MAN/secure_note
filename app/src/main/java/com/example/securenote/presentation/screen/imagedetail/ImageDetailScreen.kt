package com.example.securenote.presentation.screen.imagedetail

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.securenote.R
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar
import kotlinx.coroutines.launch

@Composable
fun ImageDetailScreen(onBackPress: () -> Boolean) {
    val viewModel: ImageDetailViewModel = hiltViewModel()
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        offset += offsetChange
    }

    val currentPage = viewModel.currentImageSelected.collectAsState()

    val pagerState = rememberPagerState(pageCount = {
        viewModel.imagePaths.size
    }, initialPage = currentPage.value)
    val scope = rememberCoroutineScope()



    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.onPageChange(page)
        }
    }

    BasePage(viewModel) {
        Box {
            Column {
                AppAppBar(
                    title = stringResource(R.string.image_detail_title),
                    onNavigationBtnClick = {
                        onBackPress()
                    })
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                    beyondViewportPageCount = 2
                ) { page ->
                    Box(
                        modifier = Modifier
                            .clip(RectangleShape)
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        scale = if (scale > 1f) {
                                            1f
                                        } else {
                                            3f
                                        }
                                    }
                                )
                            }
                            .graphicsLayer(
                                scaleX = maxOf(.5f, minOf(10f, scale)),
                                scaleY = maxOf(.5f, minOf(10f, scale)),
                                translationX = offset.x,
                                translationY = offset.y
                            )
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                                .transformable(state = state),
                            contentDescription = null,
                            model = viewModel.imagePaths[currentPage.value]
                        )
                    }
                }
            }

            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_line_arrow_left),
                    null,
                    modifier = Modifier.clickable {
                        scope.launch {
                            if (pagerState.currentPage > 0) {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage - 1,
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = FastOutSlowInEasing
                                    ),
                                )
                            }
                        }
                    },
                )
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_line_arrow_right),
                    null,
                    modifier = Modifier.clickable {
                        scope.launch {
                            val nextPage = (pagerState.currentPage + 1) % viewModel.imagePaths.size
                            pagerState.animateScrollToPage(
                                nextPage,
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                ),
                            )
                        }
                    },
                )
            }
        }
    }

}