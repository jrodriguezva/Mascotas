package es.architectcoders.mascotas.model

import android.net.Uri
import arrow.core.Either
import arrow.core.Right
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class LoginRepository(private val auth: FirebaseAuth) {

    suspend fun signIn(email: String, password: String) =
        suspendCancellableCoroutine<Either<ErrorLoginRepository, Boolean>> { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    update {
                        if (continuation.isActive) continuation.resume(Either.Right(true))
                    }
                }.addOnFailureListener {
                    if (continuation.isActive) {
                        continuation.resume(Either.Left(ErrorLoginRepository.AuthenticationError))
                    }
                }
        }

    private fun update(taskSuccess: () -> Unit) {
        if (auth.currentUser?.photoUrl == null) {
            auth.currentUser?.email?.let { email ->
                val index = email.indexOf('@')
                val nickname = email.substring(0, index)
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(nickname)
                    .setPhotoUri(Uri.parse("http://lorempixel.com/400/200"))
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

    suspend fun createAccount(email: String, password: String) =
        suspendCancellableCoroutine<Either<ErrorLoginRepository, Boolean>> { continuation ->
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                update {
                    if (continuation.isActive) continuation.resume(Either.Right(true))
                }
            }.addOnFailureListener {
                if (continuation.isActive) {
                    continuation.resume(Either.Left(ErrorLoginRepository.AuthenticationError))
                }
            }
        }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        auth.signOut()
    }

    suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
        auth.currentUser?.let {
            with(it) {
                val email = email
                val name = displayName
                val photoUrl = photoUrl?.toString()
                val phone = phoneNumber
                Right(MyFirebaseUser(email, name, photoUrl, phone))
            }
        } ?: Either.Left(ErrorLoginRepository.UserNotFoundError)
    }
}