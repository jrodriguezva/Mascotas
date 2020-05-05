package es.architectcoders.data.repository

import es.architectcoders.data.datasource.UserDataSource
import es.architectcoders.domain.User

class UserRepository(private val userDataSource: UserDataSource) {
    suspend fun getUser(email: String): User = userDataSource.getUser(email)
    suspend fun saveUser(user: User) = userDataSource.saveUser(user)
}