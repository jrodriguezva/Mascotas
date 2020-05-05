package es.architectcoders.data.repository

import es.architectcoders.data.datasource.LoginDataSource

class LoginRepository(private val loginDataSource: LoginDataSource) {

    suspend fun signIn(email: String, password: String) = loginDataSource.signIn(email, password)

    suspend fun createAccount(email: String, password: String) = loginDataSource.createAccount(email, password)

    suspend fun getCurrentUser() = loginDataSource.getCurrentUser()
}