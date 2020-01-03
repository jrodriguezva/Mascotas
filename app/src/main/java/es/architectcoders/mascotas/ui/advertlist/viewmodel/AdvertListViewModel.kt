package es.architectcoders.mascotas.ui.advertlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.architectcoders.mascotas.model.AdvertRepository
import es.architectcoders.mascotas.model.MyFirebaseAdvert
import kotlinx.coroutines.launch

class AdvertListViewModel(private val repository: AdvertRepository) : ViewModel() {

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
        TODO("Open advert ${advert.id}")
    }

    fun onAdvertFavClicked(advert: MyFirebaseAdvert) {
        TODO("Fav advert ${advert.id}")
    }

}