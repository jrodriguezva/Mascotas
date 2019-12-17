package es.architectcoders.mascotas.data.models

import android.os.Parcel
import android.os.Parcelable

class Product : Parcelable {

    var imageURL: String ?= null
    var name: String ?= null
    var prize: String ?= null

    constructor(name: String, prize: String, imageURL: String) {
        this.name = name
        this.prize = prize
        this.imageURL = imageURL
    }

    private constructor(parcel: Parcel) {
        imageURL = parcel.readString()
        name = parcel.readString()
        prize = parcel.readString()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(imageURL)
        dest.writeString(name)
        dest.writeString(prize)
    }


    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Product> = object : Parcelable.Creator<Product> {
            override fun createFromParcel(parcel: Parcel): Product {
                return Product(parcel)
            }

            override fun newArray(size: Int): Array<Product?> {
                return arrayOfNulls(size)
            }
        }
    }
}