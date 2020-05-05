package es.architectcoders.domain

data class User(
    var email: String? = "",
    val name: String? = "",
    val surname: String? = "",
    var photoUrl: String? = "",
    val rating: Int? = 0,
    val ratingCount: Int? = 0,
    var level: String? = "",
    val city: String? = "",
    val country: String? = ""
)