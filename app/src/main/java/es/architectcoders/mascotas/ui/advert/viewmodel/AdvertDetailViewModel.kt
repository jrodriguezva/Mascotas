package es.architectcoders.mascotas.ui.advert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.architectcoders.domain.Advert
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.viewmodel.ScopedViewModel
import es.architectcoders.usescases.GetAdvert
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AdvertDetailViewModel(
    private val advertId: String,
    private val getAdvert: GetAdvert,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val mError = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = mError

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mAdvert = MutableLiveData<Advert>()
    val advert: LiveData<Advert>
    get() {
        if (mAdvert.value == null) refresh()
        return mAdvert
    }

    init {
        initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    private fun refresh() {
        launch {
            mLoading.value = true
            mAdvert.postValue(getAdvert.invoke(advertId))
            mLoading.value = false
        }
    }
}