package es.architectcoders.usescases.login

import es.architectcoders.data.repository.LoginRepository

class GetCurrentUserInteractor(private val repository: LoginRepository) {
    suspend operator fun invoke() = repository.getCurrentUser()
}