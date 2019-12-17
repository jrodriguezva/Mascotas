package es.architectcoders.mascotas.data.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Product (var name: String, var prize: String, var imageURL: String) : Serializable
