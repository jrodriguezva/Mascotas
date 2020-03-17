package es.architectcoders.mascotas.ui.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.File

fun File.toBase64(): String {
    val bytes = readBytes()
    return Base64.encodeToString(bytes, Base64.NO_WRAP)
}

fun String.toImage(): Bitmap? {
    val decodedString = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}
