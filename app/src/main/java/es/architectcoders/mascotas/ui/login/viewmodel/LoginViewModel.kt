package es.architectcoders.mascotas.ui.login.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.orNull
import es.architectcoders.data.repository.ErrorLoginRepository
import es.architectcoders.data.repository.ErrorLoginRepository.AuthenticationError
import es.architectcoders.data.repository.ErrorLoginRepository.UserNotFoundError
import es.architectcoders.domain.User
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.usescases.account.CreateAccountInteractor
import es.architectcoders.usescases.login.GetCurrentUserInteractor
import es.architectcoders.usescases.login.SignInInteractor
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInInteractor: SignInInteractor,
    private val getCurrentUserInteractor: GetCurrentUserInteractor,
    private val createAccountInteractor: CreateAccountInteractor,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    companion object {
        private const val PASSWORD_MAX_LENGTH = 6
    }

    private val mEmailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = mEmailError

    private val mPassError = MutableLiveData<String?>()
    val passError: LiveData<String?> = mPassError

    private val mLoading = MutableLiveData(false)
    val loading: LiveData<Boolean> = mLoading

    private val mUser = MutableLiveData<User>()
    val user: LiveData<User> = mUser

    private val mNav = MutableLiveData<Event<Int>>()
    val nav: LiveData<Event<Int>> = mNav

    private val mError = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = mError

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            mLoading.value = true
            getCurrentUserInteractor().orNull()?.let {
                mNav.value = Event(0)
            }
            mLoading.value = false
        }
    }

    fun signIn(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        viewModelScope.launch {
            mLoading.value = true
            signInInteractor(email, password).fold(::handleFailure) { handleSuccess() }
            mLoading.value = false
        }
    }

    fun createAccount(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        viewModelScope.launch {
            mLoading.value = true
            createAccountInteractor(email, password).fold(::handleFailure) { handleSuccess() }
            mLoading.value = false
        }
    }

    private fun handleSuccess() {
        mNav.value = Event(0)
    }

    private fun handleFailure(error: ErrorLoginRepository) {
        when (error) {
            is AuthenticationError ->
                mError.value = Event(error.errorString ?: resourceProvider.getString(R.string.error_authentication))
            is UserNotFoundError ->
                mError.value = Event(resourceProvider.getString(R.string.error_user_not_found))
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
                mPassError.value = resourceProvider.getString(R.string.required)
                valid1 = false
            }
            password.length < PASSWORD_MAX_LENGTH -> {
                mPassError.value = resourceProvider.getString(R.string.error_password_length)
                valid1 = false
            }
            else -> {
                mPassError.value = null
            }
        }
        return valid1
    }

    private fun validateEmail(email: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(email) -> {
                mEmailError.value = resourceProvider.getString(R.string.required)
                valid1 = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                mEmailError.value = resourceProvider.getString(R.string.error_email_format)
                valid1 = false
            }
            else -> {
                mEmailError.value = null
            }
        }
        return valid1
    }
}
