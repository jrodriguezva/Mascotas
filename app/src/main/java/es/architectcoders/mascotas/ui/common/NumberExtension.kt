package es.architectcoders.mascotas.ui.common

import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrencyString(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}

fun String?.toCurrencyString(): String {
    val number = this?.toDoubleOrNull() ?: 0.0
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(number)
}