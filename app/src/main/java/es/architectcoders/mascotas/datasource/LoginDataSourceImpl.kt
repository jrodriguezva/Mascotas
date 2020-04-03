package es.architectcoders.mascotas.datasource

import arrow.core.Either
import arrow.core.Right
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import es.architectcoders.data.datasource.LoginDataSource
import es.architectcoders.data.repository.ErrorLoginRepository
import es.architectcoders.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class LoginDataSourceImpl(private val auth: FirebaseAuth) : LoginDataSource {

    override suspend fun signIn(email: String, password: String) =
        suspendCancellableCoroutine<Either<ErrorLoginRepository, Boolean>> { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    update {
                        if (continuation.isActive) continuation.resume(Either.Right(true))
                    }
                }.addOnFailureListener {
                    if (continuation.isActive) {
                        continuation.resume(Either.Left(ErrorLoginRepository.AuthenticationError(it.message)))
                    }
                }
        }

    override suspend fun createAccount(email: String, password: String) =
        suspendCancellableCoroutine<Either<ErrorLoginRepository, Boolean>> { continuation ->
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                update {
                    if (continuation.isActive) continuation.resume(Either.Right(true))
                }
            }.addOnFailureListener {
                if (continuation.isActive) {
                    continuation.resume(Either.Left(ErrorLoginRepository.AuthenticationError(it.message)))
                }
            }
        }

    override suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
        auth.currentUser?.let {
            with(it) {
                Right(User(email = email, name = displayName, photoUrl = photoUrl?.toString()))
            }
        } ?: Either.Left(ErrorLoginRepository.UserNotFoundError)
    }

    private fun update(taskSuccess: () -> Unit) {
        if (auth.currentUser?.photoUrl == null) {
            auth.currentUser?.email?.let { email ->
                val index = email.indexOf('@')
                val nickname = email.substring(0, index)
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(nickname)
                    .build()

                auth.currentUser?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            taskSuccess()
                        }
                    }
            }
        } else {
            taskSuccess()
        }
    }
}