package es.architectcoders.mascotas.ui.common

import android.text.TextUtils
import android.util.Patterns
import es.architectcoders.mascotas.R

class ValidatorUtilImpl : ValidatorUtil {

    companion object {
        private const val PASSWORD_MAX_LENGTH = 6

        const val MIN_LENGTH_NAME = 2
        const val MAX_LENGTH_NAME = 15
        const val MIN_LENGTH_SURNAME = 2
        const val MAX_LENGTH_SURNAME = 15
        const val MIN_LENGTH_COUNTRY = 2
        const val MAX_LENGTH_COUNTRY = 15
        const val MIN_LENGTH_CITY = 2
        const val MAX_LENGTH_CITY = 15
    }

    override fun validateEmail(email: String): Int? {
        return when {
            TextUtils.isEmpty(email) -> R.string.required
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_email_format
            else -> null
        }
    }

    override fun validatePassword(password: String): Int? {
        return when {
            TextUtils.isEmpty(password) -> R.string.required
            password.length < PASSWORD_MAX_LENGTH -> R.string.error_password_length
            else -> null
        }
    }

    override fun validateName(name: String): Int? {
        return when {
            TextUtils.isEmpty(name) -> R.string.required
            name.length < MIN_LENGTH_NAME -> R.string.error_min_name_length
            name.length > MAX_LENGTH_NAME -> R.string.error_max_name_length
            else -> null
        }
    }

    override fun validateSurname(surname: String): Int? {
        return when {
            TextUtils.isEmpty(surname) -> R.string.required
            surname.length < MIN_LENGTH_SURNAME -> R.string.error_min_surname_length
            surname.length > MAX_LENGTH_SURNAME -> R.string.error_max_surname_length
            else -> null
        }
    }

    override fun validateCity(city: String): Int? {
        return when {
            TextUtils.isEmpty(city) -> R.string.required
            city.length < MIN_LENGTH_CITY -> R.string.error_min_city_length
            city.length > MAX_LENGTH_CITY -> R.string.error_max_city_length
            else -> null
        }
    }

    override fun validateCountry(country: String): Int? {
        return when {
            TextUtils.isEmpty(country) -> R.string.required
            country.length < MIN_LENGTH_COUNTRY -> R.string.error_min_country_length
            country.length > MAX_LENGTH_COUNTRY -> R.string.error_max_country_length
            else -> null
        }
    }
}

interface ValidatorUtil {

    fun validateEmail(email: String): Int?
    fun validatePassword(password: String): Int?
    fun validateName(name: String): Int?
    fun validateSurname(surname: String): Int?
    fun validateCity(city: String): Int?
    fun validateCountry(country: String): Int?
}
