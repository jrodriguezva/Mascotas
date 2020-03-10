package es.architectcoders.mascotas.ui.login.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import arrow.core.orNull
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.model.ErrorLoginRepository
import es.architectcoders.mascotas.model.ErrorLoginRepository.AuthenticationError
import es.architectcoders.mascotas.model.ErrorLoginRepository.UserNotFoundError
import es.architectcoders.mascotas.model.LoginRepository
import es.architectcoders.mascotas.model.MyFirebaseUser
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.viewmodel.ScopedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository,
    private val resourceProvider: ResourceProvider,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    companion object {
        private const val PASSWORD_MAX_LENGTH = 6
    }

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passError = MutableLiveData<String?>()
    val passError: LiveData<String?> = _passError

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _user = MutableLiveData<MyFirebaseUser>()
    val user: LiveData<MyFirebaseUser> = _user

    private val _nav = MutableLiveData<Event<Int>>()
    val nav: LiveData<Event<Int>> = _nav

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    init {
        refresh()
    }

    private fun refresh() {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            _loading.value = true
            _user.value = repository.getCurrentUser().orNull()
            _loading.value = false
        }
    }

    fun signIn(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        viewModelScope.launch {
            _loading.value = true
            repository.signIn(email, password).fold(::handleFailure) { handleSuccess() }
            _loading.value = false
        }
    }

    fun createAccount(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        viewModelScope.launch {
            _loading.value = true
            repository.createAccount(email, password).fold(::handleFailure) { handleSuccess() }
            _loading.value = false
        }
    }

    private fun handleSuccess() {
        _nav.value = Event(0)
    }

    private fun handleFailure(error: ErrorLoginRepository) {
        when (error) {
            is AuthenticationError ->
                _error.value = Event(error.errorString ?: resourceProvider.getString(R.string.error_authentication))
            is UserNotFoundError ->
                _error.value = Event(resourceProvider.getString(R.string.error_user_not_found))
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = true
        valid = validateEmail(email, valid)
        valid = validatePassword(password, valid)

        return valid
    }

    private fun validatePassword(password: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(password) -> {
                _passError.value = resourceProvider.getString(R.string.required)
                valid1 = false
            }
            password.length < PASSWORD_MAX_LENGTH -> {
                _passError.value = resourceProvider.getString(R.string.error_password_length)
                valid1 = false
            }
            else -> {
                _passError.value = null
            }
        }
        return valid1
    }

    private fun validateEmail(email: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(email) -> {
                _emailError.value = resourceProvider.getString(R.string.required)
                valid1 = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _emailError.value = resourceProvider.getString(R.string.error_email_format)
                valid1 = false
            }
            else -> {
                _emailError.value = null
            }
        }
        return valid1
    }
}
