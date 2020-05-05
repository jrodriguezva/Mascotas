package es.architectcoders.mascotas.ui.profile.viewmodel.event

sealed class ProfileNavigationEvent {
    object ProfileNavigation : ProfileNavigationEvent()
    object PickerNavigation : ProfileNavigationEvent()
}