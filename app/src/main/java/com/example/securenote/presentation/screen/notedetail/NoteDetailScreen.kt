package com.example.securenote.presentation.screen.notedetail

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.securenote.R
import com.example.securenote.domain.enum.BlockType
import com.example.securenote.domain.enum.NoteType
import com.example.securenote.extentions.saveImageUriToInternalStorage
import com.example.securenote.presentation.base.BasePage
import com.example.securenote.presentation.dialog.CommonErrorDialog
import com.example.securenote.presentation.screen.components.AppAppBar
import com.example.securenote.presentation.screen.components.AppTextField
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun NoteDetailScreen(
    onBackPress: () -> Boolean,
    onNavigateToImageDetail: (List<String>, Int) -> Unit,
) {
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

    val scrollState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current

    var isFirstShowKeyBoard by remember { mutableStateOf(true) }

    LaunchedEffect(rootSize) {
        if (!isInitialized && rootSize.x > 0 && rootSize.y > 0) {
            offsetX = rootSize.x - fabSizePx - horizontalPadding
            offsetY = (rootSize.y - fabSizePx) / 2f
            isInitialized = true
        }
    }

    LaunchedEffect(noteDetailUiState.savedNote, noteDetailUiState.isAddImageDone) {
        if (noteDetailUiState.savedNote) {
            focusManager.clearFocus()
            focusRequester.freeFocus()
            onBackPress()
        }
    }

    LaunchedEffect(noteDetailUiState.noteBlock) {
        if (noteDetailUiState.noteBlock.isNotEmpty()) {
            delay(300)
            scrollState.animateScrollToItem(
                noteDetailUiState.noteBlock.lastIndex,
                scrollOffset = scrollState.layoutInfo.afterContentPadding
            )
            if (!scrollState.isScrollInProgress && isFirstShowKeyBoard) {
                focusRequester.requestFocus()
                keyboardController?.hide()
            }
        }
    }


    BackHandler {
        viewmodel.onSave()
    }

    val pickMultipleMedia = rememberLauncherForActivityResult(PickMultipleVisualMedia(5)) { uris ->
        // Callback is invoked after the user selects media items or closes the
        // photo picker.
        if (uris.isNotEmpty()) {
            var imagePaths = ""
            uris.forEachIndexed { index, imageUri ->
                val imagePath = context.saveImageUriToInternalStorage(imageUri)
                imagePath?.let { it ->
                    imagePaths = if (index == 0) imagePaths.plus(it) else imagePaths.plus(",$it")
                }
            }
            viewmodel.editBlockTypeImage(imagePaths)
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
                            Box {
                                DropdownMenu(
                                    expanded = noteDetailUiState.isOpenActionMenu,
                                    onDismissRequest = { viewmodel.showSelectTagBtn(false) }
                                ) {
                                    NoteType.entries
                                        .forEachIndexed { index, value ->
                                            val isSelected = value == noteDetailUiState.note.type
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        value.typeName,
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier.fillMaxSize(),
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    viewmodel.onChangeTag(value)
                                                },
                                                colors = if (isSelected) MenuDefaults.itemColors()
                                                    .copy(textColor = MaterialTheme.colorScheme.primary) else MenuDefaults.itemColors()
                                            )
                                        }
                                }
                                Row(modifier = Modifier.clickable {
                                    viewmodel.showSelectTagBtn(
                                        true
                                    )
                                }, verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        if (noteDetailUiState.note.type == NoteType.OTHER) stringResource(
                                            R.string.note_detail_select_tag
                                        ) else noteDetailUiState.note.type.typeName,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = noteDetailUiState.note.type.color,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Icon(
                                        painter = painterResource(R.drawable.ic_tag),
                                        contentDescription = null,
                                        tint = noteDetailUiState.note.type.color
                                    )
                                }
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
                        Column(
                            Modifier.padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 16.dp,
                                bottom = 32.dp
                            )
                        ) {
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
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusRequester.requestFocus()
                                    },
                                )
                            ) { title ->
                                viewmodel.onNoteTitleChange(title)
                            }
                            Spacer(Modifier.height(16.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                state = scrollState
                            ) {
                                itemsIndexed(
                                    items = noteDetailUiState.noteBlock,
                                    key = { index, block ->
                                        block.id
                                    },
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
                                                    isFirstShowKeyBoard = false
                                                    viewmodel.updateBlock(block.copy(content = value))
                                                },
                                                hint = if (noteDetailUiState.noteBlock.size == 1) stringResource(
                                                    R.string.note_detail_note_detail_hint
                                                ) else "...",
                                                modifier = if (index == noteDetailUiState.noteBlock.lastIndex) Modifier.focusRequester(
                                                    focusRequester
                                                ) else Modifier
                                            )
                                        }

                                        BlockType.IMAGE -> {
                                            ImageBlockItem(
                                                content = block.content,
                                                onImageClick = { imgUris, index ->
                                                    focusManager.clearFocus()
                                                    onNavigateToImageDetail(imgUris, index)
                                                }
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
                noteDetailUiState.noteErrorMessage?.let {
                    CommonErrorDialog(
                        show = true,
                        errorMessage = it,
                        showNegativeButton = true,
                        negativeButtonText = stringResource(R.string.cancel),
                        positiveButtonText = stringResource(R.string.back),
                        negativeButtonClick = {
                            viewmodel.clearNoteErrorMessage()
                        }) {
                        viewmodel.deleteNote()
                        viewmodel.clearNoteErrorMessage()
                        onBackPress()
                    }
                }
            },
        )
    }
}

@Composable
fun ImageBlockItem(content: String, onImageClick: (List<String>, Int) -> Unit) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = 3
    ) {
        val imagePaths = content.split(",")
        imagePaths.forEachIndexed { index, item ->
            val imagePath = "file://$item"
            AsyncImage(
                model = imagePath,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onImageClick(imagePaths, index)
                    }
                    .fillMaxSize(0.3f)
            )
        }
    }
}


