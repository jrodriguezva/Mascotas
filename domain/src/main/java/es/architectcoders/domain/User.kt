package es.architectcoders.domain

data class User(
    var email: String? = "",
    val name: String? = "",
    val photoUrl: String? = "",
    val phone: String? = ""
)