package es.architectcoders.mascotas.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Product (val name: String, val prize: String, val imageURL: String) : Parcelable
