package es.architectcoders.mascotas.ui.advert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.architectcoders.domain.Advert
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.viewmodel.event.AdvertNavigationEvent
import es.architectcoders.mascotas.ui.viewmodel.ScopedViewModel
import es.architectcoders.usescases.FindRelevantAdverts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AdvertListViewModel(
    private val findRelevantAdverts: FindRelevantAdverts,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _adverts = MutableLiveData<List<Advert>>()
    val adverts: LiveData<List<Advert>>
        get() {
            if (_adverts.value == null) refresh()
            return _adverts
        }

    private val _nav = MutableLiveData<Event<AdvertNavigationEvent>>()
    val nav: LiveData<Event<AdvertNavigationEvent>> = _nav

    init {
        initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    private fun refresh() {
        launch {
            _loading.value = true
            _adverts.value = findRelevantAdverts.invoke()
            _loading.value = false
        }
    }

    fun onAdvertClicked(advert: Advert) {
        _nav.value = Event(AdvertNavigationEvent.AdvertDetailNavigation(advert.id))
    }

    fun onCreateAdvertClicked() {
        _nav.value = Event(AdvertNavigationEvent.CreateAdvertNavigation)
    }

    fun onAdvertFavClicked(advert: Advert) {
        TODO("Fav advert ${advert.id}")
    }
}