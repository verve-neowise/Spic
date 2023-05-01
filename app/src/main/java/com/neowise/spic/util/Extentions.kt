package com.neowise.spic.util

import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.Toast

fun EditText.validateValue(fieldName: String) : String {

    val value = text.toString().trim()

    if (value.isEmpty()) {
        throw InvalidValueException("$fieldName is empty")
    }

    return value
}

fun EditText.readOnly() {
    isFocusable = true
    isFocusableInTouchMode = true
    inputType = InputType.TYPE_NULL
}

inline fun <T> buildModel(context: Context, defaultValue: T, body: () -> T) : T {
    try {
        return body()
    }
    catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }

    return defaultValue
}

fun errorToast(context: Context, exception: Exception) {
    Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
}

class InvalidValueException(message: String) : RuntimeException(message)