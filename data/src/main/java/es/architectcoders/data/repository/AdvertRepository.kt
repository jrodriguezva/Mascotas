package es.architectcoders.data.repository

import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.domain.Advert

class AdvertRepository(private val firestoreDataSource: FirestoreDataSource) {

    suspend fun getAdvert(id: String): Advert = firestoreDataSource.getAdvert(id)

    suspend fun findRelevantAdverts(): List<Advert> = firestoreDataSource.getAdverts()

    suspend fun findAdvertsByAuthor(author: String): List<Advert> = firestoreDataSource.getAdvertsByAuthor(author)

    suspend fun createAdvert(advert: Advert) = firestoreDataSource.addAdvert(advert)
}
