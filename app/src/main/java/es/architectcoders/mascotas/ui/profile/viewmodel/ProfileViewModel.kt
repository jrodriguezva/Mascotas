package es.architectcoders.mascotas.ui.profile.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import es.architectcoders.data.model.MyFirebaseAdvert
import es.architectcoders.data.repository.AdvertRepository

class ProfileViewModel(private val repository: AdvertRepository) : ViewModel() {

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