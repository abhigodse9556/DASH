package com.project.mypersonalassistant.components

import android.content.Context
import android.widget.Toast

fun showToast(
    context: Context,
    type: String,
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
) {
    val icon = when (type.lowercase()) {
        "success" -> "✅ "
        "error" -> "❌ "
        "info" -> "ℹ️ "
        "warning" -> "⚠️ "
        else -> ""
    }

    Toast.makeText(context, "$icon$message", duration).show()
}