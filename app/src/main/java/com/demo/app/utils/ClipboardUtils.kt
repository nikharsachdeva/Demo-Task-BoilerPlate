package com.demo.app.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

object ClipboardUtils {
    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // Create a ClipData object to hold the copied text.
        val clipData = ClipData.newPlainText(label, text)

        // Set the ClipData to the clipboard.
        clipboardManager.setPrimaryClip(clipData)

        // Show a toast message to indicate that the text has been copied.
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}
