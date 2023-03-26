package com.jae1jeong.currencycalculate.utils

import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChanges() = callbackFlow<String> {
    val textWatcher = doAfterTextChanged { this.trySend(it.toString()) }
    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}

fun TextView.textChanges() = callbackFlow<String> {
    val textWatcher = doAfterTextChanged { this.trySend(it.toString()) }
    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}


