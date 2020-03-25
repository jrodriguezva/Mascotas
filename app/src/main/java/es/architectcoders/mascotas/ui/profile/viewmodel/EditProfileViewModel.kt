package es.architectcoders.mascotas.ui.profile.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import arrow.core.orNull
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.User
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.profile.viewmodel.event.ProfileNavigationEvent
import es.architectcoders.usescases.GetUser
import es.architectcoders.usescases.SaveUser

class EditProfileViewModel(
    private val loginRepository: LoginRepository,
    private var getUser: GetUser,
    private var saveUser: SaveUser,
    private val resourceProvider: ResourceProvider) : ViewModel() {

    companion object {
        const val MIN_LENGTH_NAME = 5
        const val MIN_LENGTH_SURNAME = 5
        const val MIN_LENGTH_COUNTRY = 5
        const val MIN_LENGTH_CITY = 5
    }

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _nav = MutableLiveData<Event<ProfileNavigationEvent>>()
    val nav: LiveData<Event<ProfileNavigationEvent>> = _nav

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _nameError = MutableLiveData<String?>()
    val nameError: LiveData<String?> = _nameError

    private val _surnameError = MutableLiveData<String?>()
    val surnameError: LiveData<String?> = _surnameError

    private val _cityError = MutableLiveData<String?>()
    val cityError: LiveData<String?> = _cityError

    private val _countryError = MutableLiveData<String?>()
    val countryError: LiveData<String?> = _countryError

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String> = _surname

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country

    private val _rating = MutableLiveData<Int>()
    val rating: LiveData<Int> = _rating

    private val _ratingCount = MutableLiveData<String>()
    val ratingCount: LiveData<String> = _ratingCount

    private val _level = MutableLiveData<String>()
    val level: LiveData<String> = _level

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _photoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String> = _photoUrl

    private lateinit var userData: User

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            _loading.value = true
            getUser.invoke(loginRepository.getCurrentUser().orNull()?.let { user ->
                user.email?.let {
                    userData = getUser.invoke(it)

                    _name.value = userData.name
                    _surname.value = userData.surname
                    _city.value = userData.city
                    _country.value = userData.country
                    _level.value = userData.level
                    _email.value = it
                    _rating.value = userData.rating
                    _ratingCount.value = "(" + userData.ratingCount + ")"
                    _photoUrl.value = user.photoUrl
                }
            }.toString())
            _loading.value = false
        }
    }

    fun updateUser(name: String, surname: String, city: String, country: String, email: String,
                   level: String, rating: Float, ratingCount: String) {

        if (!validateForm(name, surname, country, city)) {
            return
        }
        viewModelScope.launch {
            _loading.value = true

            userData = User(name = name, surname = surname, country = country, city = city, email = email,
                level = level, rating = rating.toInt(), ratingCount = ratingCountToInt(ratingCount))
            saveUser.invoke(userData).fold(::handleFailure, ::handleSuccess)

            _loading.value = false
        }
    }



    private fun handleSuccess(user: User) {
        _nav.value = Event(ProfileNavigationEvent.ProfileNavigation)
    }

    private fun handleFailure(error: RepositoryException) {
        _error.value = Event(resourceProvider.getString(R.string.error_authentication))
    }

    private fun ratingCountToInt(ratingCount: String): Int {
        return ratingCount.replace("(", "").replace(")", "").toInt()
    }

    private fun validateForm(name: String, surname: String, country: String, city: String): Boolean {
        var valid = true
        valid = validateName(name, valid)
        valid = validateSurname(surname, valid)
        valid = validateCity(city, valid)
        valid = validateCountry(country, valid)
        return valid
    }

    private fun validateName(name: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(name) -> {
                _nameError.value = resourceProvider.getString(R.string.required)
                valid1 = false
            }
            name.length < MIN_LENGTH_NAME -> {
                _nameError.value = resourceProvider.getString(R.string.error_name_length)
                valid1 = false
            }
            else -> _nameError.value = null
        }
        return valid1
    }
    private fun validateSurname(surname: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(surname) -> {
                _surnameError.value = resourceProvider.getString(R.string.required)
                valid1 = false
            }
            surname.length < MIN_LENGTH_SURNAME -> {
                _surnameError.value = resourceProvider.getString(R.string.error_surname_length)
                valid1 = false
            }
            else -> _surnameError.value = null
        }
        return valid1
    }

    private fun validateCity(postalCode: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(postalCode) -> {
                _cityError.value = resourceProvider.getString(R.string.required)
                valid1 = false
            }
            postalCode.length < MIN_LENGTH_CITY -> {
                _cityError.value = resourceProvider.getString(R.string.error_city_length)
                valid1 = false
            }
            else -> _cityError.value = null
        }
        return valid1
    }

    private fun validateCountry(country: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(country) -> {
                _countryError.value = resourceProvider.getString(R.string.required)
                valid1 = false
            }
            country.length < MIN_LENGTH_COUNTRY -> {
                _countryError.value = resourceProvider.getString(R.string.error_country_length)

                valid1 = false
            }
            else -> _countryError.value = null
        }
        return valid1
    }
}