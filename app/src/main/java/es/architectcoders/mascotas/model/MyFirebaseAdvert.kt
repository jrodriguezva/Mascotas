package es.architectcoders.mascotas.model

data class MyFirebaseAdvert (
    var id: Long,
    var ownerId: Long,
    var title: String,
    var photoUrl: String,
    var price: Double
)