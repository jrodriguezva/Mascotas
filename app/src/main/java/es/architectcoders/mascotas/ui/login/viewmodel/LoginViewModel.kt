package es.architectcoders.mascotas.ui.login.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.orNull
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.model.ErrorLoginRepository
import es.architectcoders.mascotas.model.ErrorLoginRepository.AuthenticationError
import es.architectcoders.mascotas.model.ErrorLoginRepository.UserNotFoundError
import es.architectcoders.mascotas.model.LoginRepository
import es.architectcoders.mascotas.model.MyFirebaseUser
import es.architectcoders.mascotas.ui.common.ResourceProvider
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository, private val resourceProvider: ResourceProvider) : ViewModel() {

    sealed class UiModel {
        class Content(val user: MyFirebaseUser?) : UiModel()
        object Navigation : UiModel()
        class ValidateForm(val field: Field) : UiModel()
        class Error(val errorString: String) : UiModel()
    }

    sealed class Field {
        class Email(val error: String?) : Field()
        class Password(val error: String?) : Field()
    }

    companion object {
        private const val PASSWORD_MAX_LENGTH = 6
    }

    private val _loading = MutableLiveData<Boolean>(false)
    val loading :LiveData<Boolean> = _loading

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) getUser()
            return _model
        }

    private fun getUser() {
        viewModelScope.launch {
            _loading.value = true
            _model.value = UiModel.Content(repository.getCurrentUser().orNull())
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
        _model.value = UiModel.Navigation
    }

    private fun handleFailure(error: ErrorLoginRepository) {
        when (error) {
            is AuthenticationError -> _model.value = UiModel.Error(
                error.errorString ?: resourceProvider.getString(
                    R.string
                        .error_authentication
                )
            )
            is UserNotFoundError -> _model.value = UiModel.Error(
                resourceProvider.getString(
                    R.string
                        .error_user_not_found
                )
            )
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
                _model.value = UiModel.ValidateForm(Field.Password(resourceProvider.getString(R.string.required)))
                valid1 = false
            }
            password.length < PASSWORD_MAX_LENGTH -> {
                _model.value = UiModel.ValidateForm(Field.Password(resourceProvider.getString(R.string.error_password_length)))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Password(null))
        }
        return valid1
    }

    private fun validateEmail(email: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(email) -> {
                _model.value = UiModel.ValidateForm(Field.Email(resourceProvider.getString(R.string.required)))
                valid1 = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _model.value = UiModel.ValidateForm(Field.Email(resourceProvider.getString(R.string.error_email_format)))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Email(null))
        }
        return valid1
    }
}
