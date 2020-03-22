package es.architectcoders.usescases

import es.architectcoders.data.repository.UserRepository
import es.architectcoders.domain.User

class GetUser(private val userRepository: UserRepository) {
    suspend fun invoke(uid: String): User = userRepository.getUser(uid)
}