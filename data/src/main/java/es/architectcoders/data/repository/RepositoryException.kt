package es.architectcoders.data.repository

sealed class RepositoryException(message: String? = null) : Throwable(message) {
    object DataNotFoundException : RepositoryException()
    object NoConnectionException : RepositoryException()
}