package es.architectcoders.mascotas.application

import android.app.Application

class MascotasApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}