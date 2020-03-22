package es.architectcoders.domain

data class User(
    var email: String? = "",
    val name: String? = "",
    val photoUrl: String? = "",
    val phone: String? = "",
    val rating: Int? = 0,
    val level: Int? = 0,
    val city : String? = "",
    val country: String? = ""
)