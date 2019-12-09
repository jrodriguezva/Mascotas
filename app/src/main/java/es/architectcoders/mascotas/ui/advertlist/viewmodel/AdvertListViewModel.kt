package es.architectcoders.mascotas.ui.advertlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.architectcoders.mascotas.model.AdvertRepository
import es.architectcoders.mascotas.model.MyFirebaseAdvert

class AdvertListViewModel(private val repository: AdvertRepository) : ViewModel() {

    sealed class UiModel {
        class Content(val adverts: List<MyFirebaseAdvert>?) : UiModel()
        object Navigation : UiModel()
        class Loading(val show: Boolean) : UiModel()
        class Error(val errorString: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) getAdvert()
            return _model
        }

    private fun getAdvert() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}