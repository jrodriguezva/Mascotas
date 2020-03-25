package es.architectcoders.usescases

import arrow.core.Either
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.data.repository.UserRepository
import es.architectcoders.domain.User

class SaveUser(private val userRepository: UserRepository) {
    suspend fun invoke(user: User): Either<RepositoryException, User> = userRepository.saveUser(user)
}