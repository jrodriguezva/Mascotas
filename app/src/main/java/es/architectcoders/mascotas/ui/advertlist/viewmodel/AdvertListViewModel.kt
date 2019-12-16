package es.architectcoders.mascotas.ui.advertlist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.architectcoders.mascotas.model.AdvertRepository
import es.architectcoders.mascotas.model.MyFirebaseAdvert
import es.architectcoders.mascotas.ui.common.Scope
import kotlinx.coroutines.launch

class AdvertListViewModel(private val repository: AdvertRepository) : ViewModel(),
    Scope by Scope.Impl(){

    private val TAG = "AdvertListViewModel"
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

    init {
        initScope()
    }

    private fun refresh() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(repository.findRelevantAdverts())
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun onAdvertClicked(advert: MyFirebaseAdvert) {
        Log.d(TAG, "onAdvertClicked ${advert.id}")
//        _model.value = UiModel.Navigation(advert)
    }

    fun onAdvertFavClicked(advert: MyFirebaseAdvert) {
        Log.d(TAG, "onAdvertFavClicked ${advert.id}")

    }

}