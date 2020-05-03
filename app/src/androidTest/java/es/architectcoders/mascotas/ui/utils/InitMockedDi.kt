package es.architectcoders.mascotas.ui.utils

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import es.architectcoders.data.datasource.LoginDataSource
import es.architectcoders.data.repository.ErrorLoginRepository
import es.architectcoders.domain.User

class FakeLocalDataSource : LoginDataSource {
    private val user: User = User()

    override suspend fun signIn(email: String, password: String): Either<ErrorLoginRepository, Boolean> = when {
        email == "a@a.com" && password == "asdf123" -> Right(true)
        else -> Left(ErrorLoginRepository.UserNotFoundError)
    }

    override suspend fun createAccount(email: String, password: String) = Right(true)

    override suspend fun getCurrentUser(): Either<ErrorLoginRepository, User> = Left(ErrorLoginRepository.UserNotFoundError)
}