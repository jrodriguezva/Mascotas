package es.architectcoders.data.datasource

import arrow.core.Either
import es.architectcoders.data.repository.ErrorLoginRepository
import es.architectcoders.domain.User

interface LoginDataSource {
    suspend fun signIn(email: String, password: String): Either<ErrorLoginRepository, Boolean>
    suspend fun createAccount(email: String, password: String): Either<ErrorLoginRepository, Boolean>
    suspend fun getCurrentUser(): Either<ErrorLoginRepository, User>
}