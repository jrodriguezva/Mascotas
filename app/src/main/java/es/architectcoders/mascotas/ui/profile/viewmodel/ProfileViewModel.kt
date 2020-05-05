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
import es.architectcoders.mascotas.ui.viewmodel.ScopedViewModel
import es.architectcoders.usescases.FindAdvertsByAuthor
import es.architectcoders.usescases.GetUser
import es.architectcoders.usescases.SaveUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val findAdvertsByAuthor: FindAdvertsByAuthor,
    private val loginRepository: LoginRepository,
    private var getUser: GetUser,
    private var saveUser: SaveUser,
    private val resourceProvider: ResourceProvider,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading
    private val mAdverts = MutableLiveData<List<Advert>>()
    val adverts: LiveData<List<Advert>>
        get() {
            if (mAdverts.value == null) refresh(ProfileFragment.TYPES.ON_SALE)
            return mAdverts
        }

    private val mPhotoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String> = mPhotoUrl
    private val mTextName = MutableLiveData<String>()
    val textName: LiveData<String> = mTextName
    private val mAddress = MutableLiveData<String>()
    val address: LiveData<String> = mAddress
    private val mRating = MutableLiveData<Int>()
    val rating: LiveData<Int> = mRating
    private val mRatingCount = MutableLiveData<String>()
    val ratingCount: LiveData<String> = mRatingCount
    private val mLevel = MutableLiveData<String>()
    val level: LiveData<String> = mLevel
    private val mNav = MutableLiveData<Event<AdvertNavigationEvent>>()
    val nav: LiveData<Event<AdvertNavigationEvent>> = mNav

    lateinit var userData: User

    init {
        initScope()
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
        mTextName.value = userData.name + " " + userData.surname
        mAddress.value = userData.city + ", " + userData.country
        mLevel.value = userData.level
        mRating.value = userData.rating
        mRatingCount.value = userData.ratingCount?.intToRating()
        mPhotoUrl.value = userData.photoUrl
    }

    private fun handleFailure() {}
    private fun handleSuccess(userData: User) {
        fillUserData(userData)
    }


    fun refresh(tabSelected: ProfileFragment.TYPES) {
        viewModelScope.launch {
            mLoading.value = true
            when(tabSelected) {
                ProfileFragment.TYPES.ON_SALE -> {
                    loginRepository.getCurrentUser().orNull().let { user ->
                        mAdverts.value = user?.email?.let {
                            findAdvertsByAuthor.invoke(it) } }
                }
                ProfileFragment.TYPES.FAVORITES -> mAdverts.value = emptyList()
            }
            mLoading.value = false
        }
    }

    fun onAdvertClicked(advert: Advert) {
        mNav.value = Event(AdvertNavigationEvent.AdvertDetailNavigation(advert.id))
    }

    fun onAdvertFavClicked(advert: Advert) {
        mNav.value = Event(AdvertNavigationEvent.AdvertDetailNavigation(advert.id))
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}