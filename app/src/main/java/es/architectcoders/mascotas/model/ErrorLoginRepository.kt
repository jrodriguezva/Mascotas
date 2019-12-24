package es.architectcoders.mascotas.model

sealed class ErrorLoginRepository {
    object AuthenticationError : ErrorLoginRepository()
    object UserNotFoundError : ErrorLoginRepository()
}