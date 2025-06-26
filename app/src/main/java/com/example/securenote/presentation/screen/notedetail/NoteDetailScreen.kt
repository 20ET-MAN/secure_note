package com.example.securenote.presentation.screen.notedetail

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.securenote.R
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.screen.components.AppAppBar
import kotlin.math.roundToInt

@Composable
fun NoteDetailScreen( onBackPress: () -> Boolean) {
    val viewmodel: NoteDetailViewModel = hiltViewModel()
    val noteValue = viewmodel.noteInput.collectAsState().value

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val interactionSource = remember { MutableInteractionSource() }

    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var rootSize by remember { mutableStateOf(Offset.Zero) }
    var isInitialized by remember { mutableStateOf(false) }

    val fabSize = 48.dp
    val fabSizePx = with(LocalDensity.current) { fabSize.toPx() }

    val maxOffsetX = (rootSize.x - fabSizePx).coerceAtLeast(0f)
    val maxOffsetY = (rootSize.y - fabSizePx).coerceAtLeast(0f)

    val horizontalPadding = with(LocalDensity.current) { 24.dp.toPx() }

    val animatedOffset by animateOffsetAsState(
        targetValue = Offset(
            x = offsetX.coerceIn(0f, maxOffsetX),
            y = offsetY.coerceIn(0f, maxOffsetY)
        ),
        label = "FAB_Offset"
    )

    LaunchedEffect(rootSize) {
        if (!isInitialized && rootSize.x > 0 && rootSize.y > 0) {
            offsetX = rootSize.x - fabSizePx - horizontalPadding
            offsetY = (rootSize.y - fabSizePx) / 2f
            isInitialized = true
        }
    }

    BasePage(viewmodel) {
        Scaffold(
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) {
                            focusManager.clearFocus()
                        },
                ) {
                    AppAppBar("Note", onNavigationBtnClick = {
                        onBackPress()
                    })
                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                val size = coordinates.size
                                rootSize = Offset(size.width.toFloat(), size.height.toFloat())
                            },
                    ) {
                        BasicTextField(
                            value = noteValue,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Start
                            ),
                            onValueChange = { value ->
                                viewmodel.onNoteValueChange(value)
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .fillMaxSize()
                                .padding(horizontal = 24.dp, vertical = 16.dp),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (noteValue.isEmpty()) {
                                        Text(
                                            text = "Thêm note đi mà <3 !!!",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                textAlign = TextAlign.Start
                                            )
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                        )

                        Surface(
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        animatedOffset.x.roundToInt(),
                                        animatedOffset.y.roundToInt()
                                    )
                                }
                                .size(fabSize)
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                    }
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primary,
                            onClick = {
                                focusManager.clearFocus()
                            },
                            shadowElevation = 1.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(R.drawable.ic_gallery),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            },
        )
    }
}
