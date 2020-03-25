package es.architectcoders.data.repository

import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.domain.User

class UserRepository(private val firestoreDataSource: FirestoreDataSource) {
    suspend fun getUser(email: String): User = firestoreDataSource.getUser(email)
    suspend fun saveUser(user: User) = firestoreDataSource.saveUser(user)
}