package es.architectcoders.usescases

import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.domain.Advert

class FindRelevantAdverts(private val advertRepository: AdvertRepository) {
    suspend fun invoke(): List<Advert> = advertRepository.findRelevantAdverts()
}
