package es.architectcoders.mascotas.datasource

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.firebase.firestore.FirebaseFirestore
import es.architectcoders.data.datasource.UserDataSource
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.User
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class UserDataSourceImpl(private val database: FirebaseFirestore) : UserDataSource {

    override suspend fun getUser(email: String): User {
        return suspendCancellableCoroutine { continuation ->
            val collection = database.collection(COLLECTION_USER)
            collection.document(email)
                .get()
                .addOnSuccessListener {
                    continuation.resume(
                        it.toObject(User::class.java) ?: User())
                }
                .addOnFailureListener {
                    continuation.resume(User())
                }
        }
    }

    override suspend fun saveUser(user: User): Either<RepositoryException, User> {
        return suspendCancellableCoroutine { continuation ->
            user.email?.let {
                database.collection(COLLECTION_USER)
                    .document(it)
                    .set(user)
                    .addOnSuccessListener {
                        continuation.resume(user.right())
                    }
                    .addOnFailureListener {
                        continuation.resume(RepositoryException.NoConnectionException.left())
                    }
            }
        }
    }

    companion object {
        const val COLLECTION_USER = "user"
    }
}