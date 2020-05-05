package es.architectcoders.macotas.sharedtest.utils

import es.architectcoders.domain.Advert
import es.architectcoders.domain.User

const val MockEmail = "test@a.com"

val mockUser = User(
    email = "test@a.com",
    name = "Pepito",
    surname = "Palotes",
    photoUrl = "",
    ratingCount = 1,
    rating = 1,
    level = "Lactante, superaste nivel cachorro pero solo te alimentas de leche materna aún",
    city = "Madrid",
    country = "España"
)

val mockAdvert = Advert(
    id = "0",
    title = "Nuevo",
    photoBase64 = null,
    price = "2342",
    recent = false,
    author = MockEmail
)
val listAdvertsByAuthor = listOf(
    mockAdvert,
    mockAdvert,
    mockAdvert
)

val loading = listOf(true, false)