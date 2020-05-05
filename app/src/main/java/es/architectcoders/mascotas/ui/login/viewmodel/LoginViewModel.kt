package es.architectcoders.mascotas.ui.login.viewmodel

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
import es.architectcoders.mascotas.ui.common.ValidatorUtil
import es.architectcoders.usescases.account.CreateAccountInteractor
import es.architectcoders.usescases.login.GetCurrentUserInteractor
import es.architectcoders.usescases.login.SignInInteractor
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInInteractor: SignInInteractor,
    private val getCurrentUserInteractor: GetCurrentUserInteractor,
    private val createAccountInteractor: CreateAccountInteractor,
    private val validatorUtil: ValidatorUtil,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val mEmailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = mEmailError

    private val mPassError = MutableLiveData<String?>()
    val passError: LiveData<String?> = mPassError

    private val mLoading = MutableLiveData<Boolean>()
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
        if (!checkForm(email, password)) return
        viewModelScope.launch {
            mLoading.value = true
            signInInteractor(email, password).fold(::handleFailure) { handleSuccess() }
            mLoading.value = false
        }
    }

    fun createAccount(email: String, password: String) {
        if (!checkForm(email, password)) return
        viewModelScope.launch {
            mLoading.value = true
            createAccountInteractor(email, password).fold(::handleFailure) { handleSuccess() }
            mLoading.value = false
        }
    }

    private fun checkForm(email: String, password: String): Boolean {
        validatorUtil.validateEmail(email)?.let {
            mEmailError.value = resourceProvider.getString(it)
            return false
        }

        validatorUtil.validatePassword(password)?.let {
            mPassError.value = resourceProvider.getString(it)
            return false
        }
        return true
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
}
