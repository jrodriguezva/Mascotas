package es.architectcoders.mascotas.ui.utils

import arrow.core.*
import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.data.datasource.LoginDataSource
import es.architectcoders.data.datasource.UserDataSource
import es.architectcoders.data.repository.ErrorLoginRepository
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.Advert
import es.architectcoders.domain.User

class FakeLocalDataSource : LoginDataSource, UserDataSource {
    private val user: User = User()

    override suspend fun signIn(email: String, password: String): Either<ErrorLoginRepository, Boolean> = when {
        email == "a@a.com" && password == "asdf123" -> Right(true)
        else -> Left(ErrorLoginRepository.UserNotFoundError)
    }

    override suspend fun createAccount(email: String, password: String) = Right(true)

    override suspend fun getCurrentUser(): Either<ErrorLoginRepository, User> = Left(ErrorLoginRepository.UserNotFoundError)

    override suspend fun getUser(email: String): User = User()

    override suspend fun saveUser(user: User): Either<RepositoryException, User> = User().right()
}