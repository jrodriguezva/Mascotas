package es.architectcoders.data.model

data class MyFirebaseUser(
    var email: String? = "",
    val name: String? = "",
    val photoUrl: String? = "",
    val phone: String? = ""
)