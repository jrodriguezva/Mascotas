package es.architectcoders.usescases

import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.domain.Advert

class GetAdvert(private val advertRepository: AdvertRepository) {
    suspend fun invoke(id: String): Advert = advertRepository.getAdvert(id)
}
