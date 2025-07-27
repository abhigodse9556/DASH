package com.project.mypersonalassistant.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    startIcon: (@Composable (() -> Unit))? = null,
    endIcon: (@Composable (() -> Unit))? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            startIcon?.let {
                it()
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(text = text)

            endIcon?.let {
                Spacer(modifier = Modifier.width(8.dp))
                it()
            }
        }
    }
}
