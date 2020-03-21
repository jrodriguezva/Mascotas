package es.architectcoders.mascotas.ui.advert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.architectcoders.domain.Advert
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.usescases.GetAdvert
import kotlinx.coroutines.launch

class AdvertDetailViewModel(
    private val advertId: String,
    private val getAdvert: GetAdvert
) : ViewModel() {
    private val mError = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = mError

    private val mLoading = MutableLiveData(false)
    val loading: LiveData<Boolean> = mLoading
    val adverts = MutableLiveData(Advert())

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            mLoading.value = true
            adverts.postValue(getAdvert.invoke(advertId))
            mLoading.value = false
        }
    }
}