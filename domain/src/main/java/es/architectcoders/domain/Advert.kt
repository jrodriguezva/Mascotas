package es.architectcoders.domain

data class Advert(
    var id: String = "",
    var title: String = "",
    var photoBase64: String? = null,
    var price: String = "",
    var recent: Boolean = false,
    var author: String = ""
)