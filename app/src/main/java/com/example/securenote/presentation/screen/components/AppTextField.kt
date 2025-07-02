package com.example.securenote.presentation.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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
    onValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        value = value,
        textStyle = valueStyle,
        onValueChange = { value ->
            onValueChange(value)
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
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
        cursorBrush = cursorBrush
    )
}