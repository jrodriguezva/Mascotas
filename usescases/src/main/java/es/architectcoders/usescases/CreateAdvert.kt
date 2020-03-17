package es.architectcoders.usescases

import arrow.core.Either
import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.Advert

class CreateAdvert(private val advertRepository: AdvertRepository) {
    suspend operator fun invoke(advert: Advert): Either<RepositoryException, Advert> = advertRepository.createAdvert(advert)
}
