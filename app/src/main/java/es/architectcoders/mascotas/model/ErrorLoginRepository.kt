package es.architectcoders.mascotas.model

sealed class ErrorLoginRepository {
    class AuthenticationError(val errorString: String?) : ErrorLoginRepository()
    object UserNotFoundError : ErrorLoginRepository()
}