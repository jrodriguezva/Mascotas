package es.architectcoders.mascotas.ui.common

import android.text.TextUtils
import android.util.Patterns
import es.architectcoders.mascotas.R

class ValidatorUtil {

    companion object {
        private const val PASSWORD_MAX_LENGTH = 6
    }

    fun validateEmail(email: String): Int? {
        return when {
            TextUtils.isEmpty(email) -> R.string.required
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_email_format
            else -> null
        }
    }

    fun validatePassword(password: String): Int? {
        return when {
            TextUtils.isEmpty(password) -> R.string.required
            password.length < PASSWORD_MAX_LENGTH -> R.string.error_password_length
            else -> null
        }
    }
}