package com.example.securenote.presentation.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String? = null,
    valueStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onPrimary,
        textAlign = TextAlign.Start
    ),
    hintStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        textAlign = TextAlign.Start
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.primary),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    LaunchedEffect(value) {
        textState.value = TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        )
    }
    BasicTextField(
        value = textState.value,
        textStyle = valueStyle,
        onValueChange = { value ->
            textState.value = value
            onValueChange(value.text)
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .then(modifier),
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    Text(
                        text = hint ?: "",
                        style = hintStyle
                    )
                }
                innerTextField()
            }
        },
        keyboardOptions = keyboardOptions,
        cursorBrush = cursorBrush,
        keyboardActions = keyboardActions
    )
}