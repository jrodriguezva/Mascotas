package es.architectcoders.mascotas.datasource

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.Advert
import es.architectcoders.domain.User
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirestoreDataSourceImpl(private val database: FirebaseFirestore) : FirestoreDataSource {

    override suspend fun addAdvert(advert: Advert): Either<RepositoryException, Advert> {
        return suspendCancellableCoroutine { continuation ->
            database.collection(COLLECTION_ADVERTS)
                .add(advert)
                .addOnSuccessListener { documentReference ->
                    advert.id = documentReference.id
                    documentReference.update("id", advert.id)
                    continuation.resume(advert.right())
                }
                .addOnFailureListener {
                    continuation.resume(RepositoryException.NoConnectionException.left())
                }
        }
    }

    override suspend fun getAdverts(): List<Advert> {
        return suspendCancellableCoroutine { continuation ->
            val collection = database.collection(COLLECTION_ADVERTS)
            collection.get()
                .addOnSuccessListener {
                    continuation.resume(it.toObjects())
                }
                .addOnFailureListener {
                    continuation.resume(emptyList())
                }
        }
    }

    override suspend fun getAdvert(id: String): Advert {
        return suspendCancellableCoroutine { continuation ->
            val collection = database.collection(COLLECTION_ADVERTS)
            collection.get()
                .addOnSuccessListener {
                    continuation.resume(
                        it.toObjects<Advert>().find {
                            advert -> advert.id == id } ?: Advert())
                }
                .addOnFailureListener {
                    continuation.resume(Advert())
                }
        }
    }

    override suspend fun getAdvertsByAuthor(author: String): List<Advert> {
        return suspendCancellableCoroutine { continuation ->
            val collection = database.collection(COLLECTION_ADVERTS)
            collection.get()
                .addOnSuccessListener {
                    continuation.resume(
                        it.toObjects<Advert>().filter { advert ->
                            advert.author == author
                        })
                }
                .addOnFailureListener {
                    continuation.resume(emptyList())
                }
        }
    }

    override suspend fun getUser(uid: String): User {
        return suspendCancellableCoroutine { continuation ->
            val collection = database.collection(COLLECTION_USER)
            collection.document(uid)
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
        TODO("Not yet implemented")
    }

    companion object {
        const val COLLECTION_ADVERTS = "adverts"
        const val COLLECTION_USER = "user"
    }
}