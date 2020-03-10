package es.architectcoders.usescases

import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.domain.MyFirebaseAdvert

class FindRelevantAdverts(private val advertRepository: AdvertRepository) {
    suspend fun invoke(): List<MyFirebaseAdvert> = advertRepository.findRelevantAdverts()
}
