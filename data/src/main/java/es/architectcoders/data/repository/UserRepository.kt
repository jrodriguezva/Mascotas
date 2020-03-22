package es.architectcoders.data.repository

import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.domain.User

class UserRepository(private val firestoreDataSource: FirestoreDataSource) {
    suspend fun getUser(uid: String): User = firestoreDataSource.getUser(uid)
    suspend fun saveUser(user: User) = firestoreDataSource.saveUser(user)
}