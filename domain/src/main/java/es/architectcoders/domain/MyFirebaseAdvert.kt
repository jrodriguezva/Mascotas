package es.architectcoders.domain

import java.text.NumberFormat
import java.util.*

data class MyFirebaseAdvert(
    var id: Long,
    var title: String,
    var photoUrl: String,
    var price: Double,
    var recent: Boolean
) {
    fun priceToCurrencyString(): String {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(price)
    }
}