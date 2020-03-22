package es.architectcoders.domain

data class User(
    var uid: String? = "",
    var email: String? = "",
    val name: String? = "",
    val surname: String? = "",
    val photoUrl: String? = "",
    val phone: String? = "",
    val rating: Int? = 0,
    val ratingCount: Int? = 0,
    val level: String? = "",
    val city : String? = "",
    val country: String? = "",
    val photoBase64: String? = "",
    val author: String? = ""
)