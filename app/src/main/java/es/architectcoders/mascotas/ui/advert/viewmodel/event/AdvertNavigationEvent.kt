package es.architectcoders.mascotas.ui.advert.viewmodel.event

    sealed class AdvertNavigationEvent {
        object CreateAdvertNavigation : AdvertNavigationEvent()
        class AdvertDetailNavigation(val id: String) : AdvertNavigationEvent()
    }