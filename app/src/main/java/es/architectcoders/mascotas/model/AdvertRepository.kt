package es.architectcoders.mascotas.model

import kotlinx.coroutines.delay

class AdvertRepository {
    suspend fun findRelevantAdverts() : List<MyFirebaseAdvert> {
        delay(2000)
        return findDummyAdverts()
    }

    private fun findDummyAdverts() = listOf (
        MyFirebaseAdvert(0, "Advert title 1", "", 10.5, true),
        MyFirebaseAdvert(1, "Advert title 2", "", 25.000, true),
        MyFirebaseAdvert(2, "Advert title 3", "", 19.999, false)
        )
}
