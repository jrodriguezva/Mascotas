package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import arrow.core.orNull
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.domain.User
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.common.ValidatorUtil
import es.architectcoders.mascotas.ui.common.intToRating
import es.architectcoders.mascotas.ui.common.ratingCountToInt
import es.architectcoders.mascotas.ui.profile.viewmodel.event.ProfileNavigationEvent
import es.architectcoders.usescases.GetUser
import es.architectcoders.usescases.SaveUser

class EditProfileViewModel(
    private val validatorUtil: ValidatorUtil,
    private val loginRepository: LoginRepository,
    private var getUser: GetUser,
    private var saveUser: SaveUser,
    private val resourceProvider: ResourceProvider) : ViewModel() {

    private val mError = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = mError

    private val mNav = MutableLiveData<Event<ProfileNavigationEvent>>()
    val nav: LiveData<Event<ProfileNavigationEvent>> = mNav

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mNameError = MutableLiveData<String?>()
    val nameError: LiveData<String?> = mNameError

    private val mSurnameError = MutableLiveData<String?>()
    val surnameError: LiveData<String?> = mSurnameError

    private val mCityError = MutableLiveData<String?>()
    val cityError: LiveData<String?> = mCityError

    private val mCountryError = MutableLiveData<String?>()
    val countryError: LiveData<String?> = mCountryError

    private val mName = MutableLiveData<String>()
    val name: LiveData<String> = mName

    private val mSurname = MutableLiveData<String>()
    val surname: LiveData<String> = mSurname

    private val mCity = MutableLiveData<String>()
    val city: LiveData<String> = mCity

    private val mCountry = MutableLiveData<String>()
    val country: LiveData<String> = mCountry

    private val mRating = MutableLiveData<Int>()
    val rating: LiveData<Int> = mRating

    private val mRatingCount = MutableLiveData<String>()
    val ratingCount: LiveData<String> = mRatingCount

    private val mLevel = MutableLiveData<String>()
    val level: LiveData<String> = mLevel

    private val mEmail = MutableLiveData<String>()
    val email: LiveData<String> = mEmail

    private val mPhotoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String> = mPhotoUrl

    lateinit var userData: User

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            mLoading.value = true
            getUser.invoke(loginRepository.getCurrentUser().orNull()?.let { user ->
                user.email?.let {
                    userData = getUser.invoke(it)

                    mName.value = userData.name
                    mSurname.value = userData.surname
                    mCity.value = userData.city
                    mCountry.value = userData.country
                    mLevel.value = userData.level
                    mEmail.value = it
                    mRating.value = userData.rating
                    mRatingCount.value = userData.ratingCount?.intToRating()
                    mPhotoUrl.value = userData.photoUrl
                }
            }.toString())
            mLoading.value = false
        }
    }

    fun updateUser(name: String, surname: String, city: String, country:String) {

        if (!checkForm(name, surname, city, country)) {
            return
        }

        viewModelScope.launch {
            mLoading.value = true

            userData = User(name = name, surname = surname, country = country, city = city,
                email = mEmail.value, level = mLevel.value, rating = mRating.value?.toInt(),
                ratingCount = mRatingCount.value?.ratingCountToInt(), photoUrl = mPhotoUrl.value)
            
            saveUser.invoke(userData).fold( { handleFailure() }, { handleSuccess() } )

            mLoading.value = false
        }
    }

    private fun handleSuccess() {
        mNav.value = Event(ProfileNavigationEvent.ProfileNavigation)
    }

    private fun handleFailure() {
        mError.value = Event(resourceProvider.getString(R.string.error_save_user))
    }

    private fun checkForm(name: String, surname: String, city: String, country: String): Boolean {
        validatorUtil.validateName(name)?.let {
            mNameError.value = resourceProvider.getString(it)
            return false
        }

        validatorUtil.validateSurname(surname)?.let {
            mSurnameError.value = resourceProvider.getString(it)
            return false
        }
        validatorUtil.validateCity(city)?.let {
            mCityError.value = resourceProvider.getString(it)
            return false
        }
        validatorUtil.validateCountry(country)?.let {
            mCountryError.value = resourceProvider.getString(it)
            return false
        }
        return true
    }

    fun clickOnPicker() {
        mNav.value = Event(ProfileNavigationEvent.PickerNavigation)
    }

    fun setImage(image: String?) {
        mPhotoUrl.value = image
    }
}