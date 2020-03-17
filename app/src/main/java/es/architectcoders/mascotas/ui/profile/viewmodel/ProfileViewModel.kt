package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.architectcoders.domain.Advert
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.usescases.FindRelevantAdverts
import kotlinx.coroutines.launch

class ProfileViewModel(private val findRelevantAdverts: FindRelevantAdverts) : ViewModel() {
    private val _nav = MutableLiveData<Event<String>>()
    val nav: LiveData<Event<String>> = _nav
    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val adverts: List<Advert>) : UiModel()
        object Navigation : UiModel()
        class Error(val errorString: String) : UiModel()
    }

    private fun refresh() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(findRelevantAdverts.invoke())
        }
    }

    fun onAdvertClicked(advert: Advert) {
        _nav.value = Event(advert.id)
    }

    fun onAdvertFavClicked(advert: Advert) {
        _nav.value = Event(advert.id)
    }
}