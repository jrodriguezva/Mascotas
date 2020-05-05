package es.architectcoders.data.datasource

import arrow.core.Either
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.User


interface UserDataSource {
    suspend fun getUser(email: String): User
    suspend fun saveUser(user: User): Either<RepositoryException, User>
}