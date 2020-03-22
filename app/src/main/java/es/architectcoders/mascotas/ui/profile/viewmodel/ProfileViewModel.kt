package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.architectcoders.domain.Advert
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.viewmodel.event.AdvertNavigationEvent
import es.architectcoders.mascotas.ui.profile.fragments.ProfileFragment
import es.architectcoders.usescases.FindRelevantAdverts
import kotlinx.coroutines.launch

class ProfileViewModel(private val findRelevantAdverts: FindRelevantAdverts) : ViewModel() {

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading
    private val _adverts = MutableLiveData<List<Advert>>()
    val adverts: LiveData<List<Advert>> = _adverts
    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address
    private val _rating = MutableLiveData<Int>()
    val rating: LiveData<Int> = _rating
    private val _level = MutableLiveData<String>()
    val level: LiveData<String> = _level
    private val _nav = MutableLiveData<Event<AdvertNavigationEvent>>()
    val nav: LiveData<Event<AdvertNavigationEvent>> = _nav

    init {
        refresh(ProfileFragment.LIST_TIPES.ON_SALE)
    }

    fun refresh(tabSelected: ProfileFragment.LIST_TIPES) {
        viewModelScope.launch {
            _loading.value = true
            when(tabSelected) {
                ProfileFragment.LIST_TIPES.ON_SALE -> _adverts.value = findRelevantAdverts.invoke()
                ProfileFragment.LIST_TIPES.FAVORITES ->_adverts.value = emptyList()
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