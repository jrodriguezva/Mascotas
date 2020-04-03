package es.architectcoders.usescases.login

import es.architectcoders.data.repository.LoginRepository

class SignInInteractor(private val repository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.signIn(email, password)
}