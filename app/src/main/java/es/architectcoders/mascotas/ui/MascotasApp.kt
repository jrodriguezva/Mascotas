package es.architectcoders.mascotas.ui

import android.app.Application

class MascotasApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}