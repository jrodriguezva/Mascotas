package es.architectcoders.usescases

import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.domain.Advert

class FindAdvertsByAuthor(private val advertRepository: AdvertRepository) {
    suspend fun invoke(author: String): List<Advert> = advertRepository.findAdvertsByAuthor(author)
}
