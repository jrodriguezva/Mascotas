package es.architectcoders.data.datasource

import arrow.core.Either
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.Advert
import es.architectcoders.domain.User

interface FirestoreDataSource {
    suspend fun addAdvert(advert: Advert): Either<RepositoryException, Advert>
    suspend fun getAdverts(): List<Advert>
    suspend fun getAdvert(id: String): Advert
    suspend fun getAdvertsByAuthor(author: String): List<Advert>
}