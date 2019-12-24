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
import es.architectcoders.mascotas.model.LoginRepository
import es.architectcoders.mascotas.model.MyFirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    sealed class UiModel {
        class Content(val user: MyFirebaseUser?) : UiModel()
        object Navigation : UiModel()
        class ValidateForm(val field: Field) : UiModel()
        class Loading(val show: Boolean) : UiModel()
        class Error(val errorString: Int) : UiModel()
    }

    sealed class Field {
        class Email(val error: Int?) : Field()
        class Password(val error: Int?) : Field()
    }

    companion object{
        private const val PASSWORD_MAX_LENGTH = 6
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) getUser()
            return _model
        }

    private fun getUser() {
        viewModelScope.launch {
            _model.value = UiModel.Loading(true)
            _model.value = UiModel.Content(repository.getCurrentUser().orNull())
            _model.value = UiModel.Loading(false)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
            _model.value = UiModel.Content(null)
        }
    }

    fun signIn(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        viewModelScope.launch {
            _model.value = UiModel.Loading(true)
            repository.signIn(email, password).fold(::handleFailure) { handleSuccess() }
            _model.value = UiModel.Loading(false)
        }
    }

    fun createAccount(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        viewModelScope.launch {
            _model.value = UiModel.Loading(true)
            repository.createAccount(email, password).fold(::handleFailure) { handleSuccess() }
            _model.value = UiModel.Loading(false)
        }
    }

    private fun handleSuccess() {
        _model.value = UiModel.Navigation
    }

    private fun handleFailure(error: ErrorLoginRepository) {
        when (error) {
            ErrorLoginRepository.AuthenticationError -> _model.value = UiModel.Error(R.string.error_authentication)
            ErrorLoginRepository.UserNotFoundError -> _model.value = UiModel.Error(R.string.error_user_not_found)
        }
        _model.value = UiModel.Content(null)
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
                _model.value = UiModel.ValidateForm(Field.Password(R.string.required))
                valid1 = false
            }
            password.length < PASSWORD_MAX_LENGTH -> {
                _model.value = UiModel.ValidateForm(Field.Password(R.string.error_password_length))
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
                _model.value = UiModel.ValidateForm(Field.Email(R.string.required))
                valid1 = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _model.value = UiModel.ValidateForm(Field.Email(R.string.error_email_format))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Email(null))
        }
        return valid1
    }
}
