package es.architectcoders.mascotas.model

data class MyFirebaseUser(
    var email: String? = "",
    val name: String? = "",
    val photoUrl: String? = "",
    val phone: String? = ""
)