package es.architectcoders.mascotas.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

fun String.decode(): String {
    return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
}

fun String.encode(): String {
    return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
}

internal fun encodeImageBitmap(bm: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 60, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT).trim()
}
internal fun encodeImagePath(path: String): String? {
    val imagefile = File(path)
    var fis: FileInputStream? = null
    try {
        fis = FileInputStream(imagefile)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    val bm = BitmapFactory.decodeStream(fis)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 60, baos)
    val b = baos.toByteArray()
    //Base64.de
    return Base64.encodeToString(b, Base64.DEFAULT)
}