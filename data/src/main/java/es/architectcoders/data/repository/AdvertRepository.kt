package es.architectcoders.data.repository

import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.domain.Advert


class AdvertRepository(private val firestoreDataSource: FirestoreDataSource) {
    suspend fun findRelevantAdverts(): List<Advert> {
        return findDummyAdverts()
    }

    private fun findDummyAdverts() = listOf(
        Advert("0", "Advert title 1", "https://picsum.photos/id/112/256", "10.5", true),
        Advert("1", "Advert title 2", "https://picsum.photos/id/228/256", "25.000", true),
        Advert("2", "Advert title 3", "https://picsum.photos/id/1080/256", "19.999", false)
    )

    suspend fun createAdvert(advert: Advert) = firestoreDataSource.addAdvert(advert)
}
