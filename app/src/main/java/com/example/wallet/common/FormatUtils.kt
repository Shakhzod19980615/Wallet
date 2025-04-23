package com.example.wallet.common

import androidx.compose.runtime.remember
import java.text.NumberFormat
import java.util.Locale

fun formatBalance(amount: Double): String {
    return NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 3
        maximumFractionDigits = 3
    }.format(amount)
}
fun formatCardNumber(raw: String): String {
    return raw.chunked(4).joinToString(" ")
}
