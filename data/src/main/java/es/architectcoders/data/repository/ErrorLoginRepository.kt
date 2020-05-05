package es.architectcoders.data.repository

sealed class ErrorLoginRepository {
    class AuthenticationError(val errorString: String?) : ErrorLoginRepository()
    object UserNotFoundError : ErrorLoginRepository()
}