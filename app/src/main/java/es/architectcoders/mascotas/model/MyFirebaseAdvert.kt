package es.architectcoders.mascotas.model

import java.text.NumberFormat
import java.util.Locale

data class MyFirebaseAdvert(
    var id: Long,
    var title: String,
    var photoUrl: String,
    var price: Double,
    var recent: Boolean
) {
    fun getPrice(): String = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(price)
}