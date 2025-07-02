package com.example.securenote.presentation.screen.notedetail

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.securenote.R
import com.example.securenote.domain.enum.BlockType
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.helper.saveImageUriToInternalStorage
import com.example.securenote.presentation.screen.components.AppAppBar
import com.example.securenote.presentation.screen.components.AppTextField
import kotlin.math.roundToInt

@Composable
fun NoteDetailScreen(onBackPress: () -> Boolean) {
    val context = LocalContext.current
    val viewmodel: NoteDetailViewModel = hiltViewModel()
    val noteDetailUiState = viewmodel.noteDetailUiState.collectAsState().value

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

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
            x = offsetX.coerceIn(0f, maxOffsetX), y = offsetY.coerceIn(0f, maxOffsetY)
        ), label = "FAB_Offset"
    )

    LaunchedEffect(rootSize) {
        if (!isInitialized && rootSize.x > 0 && rootSize.y > 0) {
            offsetX = rootSize.x - fabSizePx - horizontalPadding
            offsetY = (rootSize.y - fabSizePx) / 2f
            isInitialized = true
        }
    }

    LaunchedEffect(noteDetailUiState.savedNote) {
        if (noteDetailUiState.savedNote) {
            focusManager.clearFocus()
            onBackPress()
        }
    }

    val pickMultipleMedia = rememberLauncherForActivityResult(PickMultipleVisualMedia(5)) { uris ->
        // Callback is invoked after the user selects media items or closes the
        // photo picker.
        if (uris.isNotEmpty()) {
            uris.forEach { imageUri ->
                val imagePath = context.saveImageUriToInternalStorage(imageUri)
                imagePath?.let { it ->
/*                    val block = NoteBlock(
                        id = ,
                        noteId = viewmodel.noteId,
                        order = 1,
                        type = BlockType.IMAGE,
                        content = it
                    )*/
                }
            }
            Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
        } else {
            Log.d("PhotoPicker", "No media selected")
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
                    AppAppBar(
                        "Note", onNavigationBtnClick = {
                            viewmodel.onSave()
                        }, actionButton = {
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(6.dp)
                            ) {
                                Text(
                                    "SAVE",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = Bold
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    painter = painterResource(R.drawable.ic_save_ouline),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                        }, onActionBtnClick = {
                            viewmodel.onSave()
                        }
                    )
                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                val size = coordinates.size
                                rootSize = Offset(size.width.toFloat(), size.height.toFloat())
                            },
                    ) {
                        Column {
                            AppTextField(
                                value = noteDetailUiState.note.title,
                                hint = "Tiêu đề!!!",
                                valueStyle = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Start
                                ),
                                hintStyle = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    textAlign = TextAlign.Start
                                ),
                            ) { title ->
                                viewmodel.onNoteTitleChange(title)
                            }
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                itemsIndexed(
                                    items = noteDetailUiState.noteBlock,
                                    key = { index, block ->
                                        block.id
                                    }
                                ) { index, block ->
                                    when (block.type) {
                                        BlockType.TEXT -> {
                                            AppTextField(
                                                value = block.content,
                                                valueStyle = MaterialTheme.typography.bodyLarge.copy(
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    textAlign = TextAlign.Start
                                                ),
                                                onValueChange = { value ->
                                                    viewmodel.updateBlock(block.copy(content = value))
                                                },
                                                hint = "Thêm note đi mà <3 !!!",
                                                modifier = if (index == noteDetailUiState.noteBlock.lastIndex) Modifier.focusRequester(
                                                    focusRequester
                                                ) else Modifier
                                            )
                                        }

                                        BlockType.IMAGE -> {
                                            ImageBlockItem(
                                                imagePath = block.content
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Surface(
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        animatedOffset.x.roundToInt(), animatedOffset.y.roundToInt()
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
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable {
                                            pickMultipleMedia.launch(
                                                PickVisualMediaRequest(
                                                    PickVisualMedia.ImageOnly
                                                )
                                            )
                                        },
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

@Composable
fun ImageBlockItem(imagePath: String) {
    Image(
        painter = rememberAsyncImagePainter("file://$imagePath"),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    )
}