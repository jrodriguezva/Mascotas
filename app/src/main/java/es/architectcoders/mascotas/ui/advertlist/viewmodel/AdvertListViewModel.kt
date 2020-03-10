package es.architectcoders.mascotas.ui.advertlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.architectcoders.domain.MyFirebaseAdvert
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.usescases.FindRelevantAdverts
import kotlinx.coroutines.launch

class AdvertListViewModel(private val findRelevantAdverts: FindRelevantAdverts) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading
    private val _adverts = MutableLiveData<List<MyFirebaseAdvert>>()
    val adverts: LiveData<List<MyFirebaseAdvert>> = _adverts
    private val _nav = MutableLiveData<Event<Long>>()
    val nav: LiveData<Event<Long>> = _nav
    init {
        refresh()
    }
    private fun refresh() {
        viewModelScope.launch {
            _loading.value = true
            _adverts.value = findRelevantAdverts.invoke()
            _loading.value = false
        }
    }

    fun onAdvertClicked(advert: MyFirebaseAdvert) {
        _nav.value = Event(advert.id)
    }

    fun onAdvertFavClicked(advert: MyFirebaseAdvert) {
        TODO("Fav advert ${advert.id}")
    }
}