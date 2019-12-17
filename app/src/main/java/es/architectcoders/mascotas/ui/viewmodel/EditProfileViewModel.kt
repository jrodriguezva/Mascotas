package es.architectcoders.mascotas.ui.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.architectcoders.mascotas.ui.model.EditProfileRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class EditProfileViewModel(private val repository: EditProfileRepository): ViewModel() {
    sealed class UiModel {
        object Navigation : UiModel()
        class ValidateForm(val field: Field) : UiModel()
        class Loading(val show: Boolean) : UiModel()
        class Error(val errorString: String) : UiModel()
    }

    sealed class Field {
        class Name(val error: String?) : Field()
        class Surname(val error: String?) : Field()
        class Address(val error: String?) : Field()
        class PostalCode(val error: String?) : Field()
        class Email(val error: String?) : Field()
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
            //_model.value = UiModel.Content(repository.getCurrentUser().orNull())
            _model.value = UiModel.Loading(false)
        }
    }

    fun updateUser(name: String, surname: String, address: String, postalCode: String, email: String) {
        if (!validateForm(name, surname, address, postalCode, email)) {
            return
        }
        viewModelScope.launch {
            _model.value = UiModel.Loading(true)
            //repository.updateUser(name, surname, address, postalCode, email).fold(::handleFailure, ::handleSuccess)
            _model.value = UiModel.Loading(false)
        }
    }

    private fun handleSuccess(signInResult: Boolean) {
        _model.value = UiModel.Navigation
    }

    private fun handleFailure(error: String) {
        _model.value = UiModel.Error(error)
        //_model.value = UiModel.Content(null)
    }

    private fun validateForm(name: String, surname: String, address: String, postalCode: String, email: String): Boolean {
        var valid = true
        valid = validateName(name, valid)
        valid = validateSurname(surname, valid)
        valid = validateAddress(address, valid)
        valid = validatePostalCode(postalCode, valid)
        valid = validateEmail(email, valid)
        return valid
    }

    private fun validateName(name: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(name) -> {
                _model.value = UiModel.ValidateForm(Field.Name("Required."))
                valid1 = false
            }
            name.length < MAX_LENGTH_NAME -> {
                _model.value = UiModel.ValidateForm(Field.Name("Name should be at least 6 character."))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Name(null))
        }
        return valid1
    }
    private fun validateSurname(surname: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(surname) -> {
                _model.value = UiModel.ValidateForm(Field.Surname("Required."))
                valid1 = false
            }
            surname.length < MAX_LENGTH_SURNAME -> {
                _model.value = UiModel.ValidateForm(Field.Surname("Surname should be at least 6 character."))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Surname(null))
        }
        return valid1
    }
    private fun validateAddress(address: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(address) -> {
                _model.value = UiModel.ValidateForm(Field.Address("Required."))
                valid1 = false
            }
            address.length < MAX_LENGTH_ADDRESS -> {
                _model.value = UiModel.ValidateForm(Field.Address("address should be at least 6 character."))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Address(null))
        }
        return valid1
    }
    private fun validatePostalCode(postalCode: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(postalCode) -> {
                _model.value = UiModel.ValidateForm(Field.PostalCode("Required."))
                valid1 = false
            }
            postalCode.length < MAX_LENGTH_POSTAL_CODE -> {
                _model.value = UiModel.ValidateForm(Field.PostalCode("The postal code should be at least 4 character."))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.PostalCode(null))
        }
        return valid1
    }
    private fun validateEmail(email: String, valid: Boolean): Boolean {
        var valid1 = valid
        when {
            TextUtils.isEmpty(email) -> {
                _model.value = UiModel.ValidateForm(Field.Email("Required."))
                valid1 = false
            }
            email.length < MAX_LENGTH_EMAIL -> {
                _model.value = UiModel.ValidateForm(Field.Email("The email should be at least 6 character."))
                valid1 = false
            }
            else -> _model.value = UiModel.ValidateForm(Field.Email(null))
        }
        return valid1
    }

    companion object {
        const val MAX_LENGTH_NAME = 6
        const val MAX_LENGTH_SURNAME = 6
        const val MAX_LENGTH_ADDRESS = 6
        const val MAX_LENGTH_POSTAL_CODE = 4
        const val MAX_LENGTH_EMAIL = 6
    }
}