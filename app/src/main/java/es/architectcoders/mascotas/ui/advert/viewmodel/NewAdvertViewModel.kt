package es.architectcoders.mascotas.ui.advert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.orNull
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.Advert
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.advert.viewmodel.event.NewAdvertNavigationEvent
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.usescases.CreateAdvert
import kotlinx.coroutines.launch

class NewAdvertViewModel(
    private val loginRepository: LoginRepository,
    private val createAdvert: CreateAdvert,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val mError = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = mError

    private val mLoading = MutableLiveData(false)
    val loading: LiveData<Boolean> = mLoading

    private val mNav = MutableLiveData<Event<NewAdvertNavigationEvent>>()
    val nav: LiveData<Event<NewAdvertNavigationEvent>> = mNav

    val adverts = MutableLiveData(Advert())

    fun createAdvert() {
        viewModelScope.launch {
            mLoading.value = true
            loginRepository.getCurrentUser().orNull().let { user ->
                adverts.value?.author = user?.email.toString()
                adverts.value?.let {
                    createAdvert(it).fold(::handleFailure, ::handleSuccess)
                }
            }
            mLoading.value = false
        }
    }

    private fun handleSuccess(advert: Advert) {
        mNav.value = Event(NewAdvertNavigationEvent.CreateAdvertNavigation(advert))
    }

    private fun handleFailure(error: RepositoryException) {
        mError.value = Event(resourceProvider.getString(R.string.error_authentication))
    }

    fun clickOnPicker() {
        mNav.value = Event(NewAdvertNavigationEvent.PickerNavigation)
    }

    fun setImage(image: String?) {
        val advert = adverts.value
        advert?.photoBase64 = image
        adverts.postValue(advert)
    }
}