package es.architectcoders.mascotas.ui.advert.viewmodel.event

import es.architectcoders.domain.Advert

sealed class NewAdvertNavigationEvent {
        object PickerNavigation : NewAdvertNavigationEvent()
        class CreateAdvertNavigation(val advert: Advert) : NewAdvertNavigationEvent()
    }