package com.project.mypersonalassistant.components

import android.graphics.fonts.Font
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.input.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(8.dp),

    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Determine label color based on focus or error state
    val labelColor = when {
        isError -> Color.Red
        isFocused -> Color.Blue
        !enabled -> Color.White
        else -> Color.Gray
    }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(
            label,
            color = labelColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )},
        interactionSource = interactionSource,
        modifier = modifier,
        visualTransformation = visualTransformation,
        readOnly = readOnly,
        enabled = enabled,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(12.dp), // border radius
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.LightGray,
            errorBorderColor = Color.Red,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.LightGray, // background color
        )
    )
}