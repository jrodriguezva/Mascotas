package es.architectcoders.data.repository

import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.domain.Advert

class AdvertRepository(private val firestoreDataSource: FirestoreDataSource) {
    suspend fun findRelevantAdverts(): List<Advert> = firestoreDataSource.getAdverts()

    suspend fun createAdvert(advert: Advert) = firestoreDataSource.addAdvert(advert)
}
