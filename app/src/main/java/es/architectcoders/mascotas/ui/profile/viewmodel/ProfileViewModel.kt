package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import es.architectcoders.domain.MyFirebaseAdvert
import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.mascotas.ui.Event

class ProfileViewModel(private val repository: AdvertRepository) : ViewModel() {
    private val _nav = MutableLiveData<Event<Long>>()
    val nav: LiveData<Event<Long>> = _nav
    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val adverts: List<MyFirebaseAdvert>) : UiModel()
        object Navigation : UiModel()
        class Error(val errorString: String) : UiModel()
    }

    private fun refresh() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(repository.findRelevantAdverts())
        }
    }

    fun onAdvertClicked(advert: MyFirebaseAdvert) {
        _nav.value = Event(advert.id)
    }

    fun onAdvertFavClicked(advert: MyFirebaseAdvert) {
        _nav.value = Event(advert.id)
    }
}