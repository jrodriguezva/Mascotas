package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.orNull
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.domain.Advert
import es.architectcoders.domain.User
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.viewmodel.event.AdvertNavigationEvent
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.common.intToRating
import es.architectcoders.mascotas.ui.profile.fragments.ProfileFragment
import es.architectcoders.usescases.FindAdvertsByAuthor
import es.architectcoders.usescases.GetUser
import es.architectcoders.usescases.SaveUser
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val findAdvertsByAuthor: FindAdvertsByAuthor,
    private val loginRepository: LoginRepository,
    private var getUser: GetUser,
    private var saveUser: SaveUser,
    private val resourceProvider: ResourceProvider) : ViewModel() {

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading
    private val _adverts = MutableLiveData<List<Advert>>()
    val adverts: LiveData<List<Advert>> = _adverts

    private val _photoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String> = _photoUrl
    private val _textName = MutableLiveData<String>()
    val textName: LiveData<String> = _textName
    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address
    private val _rating = MutableLiveData<Int>()
    val rating: LiveData<Int> = _rating
    private val _ratingCount = MutableLiveData<String>()
    val ratingCount: LiveData<String> = _ratingCount
    private val _level = MutableLiveData<String>()
    val level: LiveData<String> = _level
    private val _nav = MutableLiveData<Event<AdvertNavigationEvent>>()
    val nav: LiveData<Event<AdvertNavigationEvent>> = _nav

    lateinit var userData: User

    init {
        getUserData()
        refresh(ProfileFragment.TYPES.ON_SALE)
    }

    fun getUserData() {
        viewModelScope.launch {
            getUser.invoke(loginRepository.getCurrentUser().orNull()?.let { user ->
                user.email?.let {
                    userData = getUser.invoke(it)

                    if (userData.email.isNullOrBlank()) {
                        user.level = resourceProvider.getString(R.string.base_level_user)
                        saveUser.invoke(user).fold( { handleFailure() }, { handleSuccess(user) } )
                    } else {
                        fillUserData(userData)
                    }
                }
            }.toString())
        }
    }

    private fun fillUserData(userData: User) {
        _textName.value = userData.name + " " + userData.surname
        _address.value = userData.city + ", " + userData.country
        _level.value = userData.level
        _rating.value = userData.rating
        _ratingCount.value = userData.ratingCount?.intToRating()
        _photoUrl.value = userData.photoUrl
    }

    private fun handleFailure() {}
    private fun handleSuccess(userData: User) {
        fillUserData(userData)
    }


    fun refresh(tabSelected: ProfileFragment.TYPES) {
        viewModelScope.launch {
            _loading.value = true
            when(tabSelected) {
                ProfileFragment.TYPES.ON_SALE -> {
                    loginRepository.getCurrentUser().orNull().let { user ->
                        _adverts.value = user?.email?.let {
                            findAdvertsByAuthor.invoke(it) } }
                }
                ProfileFragment.TYPES.FAVORITES -> _adverts.value = emptyList()
            }
            _loading.value = false
        }
    }

    fun onAdvertClicked(advert: Advert) {
        _nav.value = Event(AdvertNavigationEvent.AdvertDetailNavigation(advert.id))
    }

    fun onAdvertFavClicked(advert: Advert) {
        _nav.value = Event(AdvertNavigationEvent.AdvertDetailNavigation(advert.id))
    }
}