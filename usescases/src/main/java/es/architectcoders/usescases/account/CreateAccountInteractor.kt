package es.architectcoders.usescases.account

import es.architectcoders.data.repository.LoginRepository

class CreateAccountInteractor constructor(private val repository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.createAccount(email, password)
}