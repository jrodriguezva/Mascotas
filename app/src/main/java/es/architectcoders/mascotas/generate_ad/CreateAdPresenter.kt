package es.architectcoders.mascotas.generate_ad

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.graphics.decodeBitmap
import es.architectcoders.mascotas.Utils.encodeImageBitmap
import es.architectcoders.mascotas.model.ad


class CreateAdPresenter(var view: CreateAdView, var context: Context) {
    var dataList = ArrayList<String>()
    fun onStart() {
        view.setupFields()
    }

    fun saveButtonPressed(newAd: ad) {
        when {
            checkFields(newAd) && dataList.isNotEmpty() -> {
                newAd.imgUriList = dataList
                view.saveAd(newAd)
            }
            else -> {
                view.showError()
            }
        }
    }

    private fun checkFields(ad: ad): Boolean =
        !(ad.title.isEmpty() || ad.description.isEmpty() || ad.prize <= 0)


    fun clickedPhoto(photoId: Int) {
        view.showPopup(photoId)
    }

    fun dataRecived(
        data: Intent?,
        requestCode: Int
    ) {
        val selected: Uri? = data?.data
        val cameraImage = data?.extras
        var base64 = ""
        when (requestCode) {
            CreateAdFragment.IMAGE_CAPTURE_CODE -> {
                val bitmap = cameraImage?.get("data") as Bitmap
                base64 = encodeImageBitmap(bitmap)!!

            }
            CreateAdFragment.IMAGE_PICK_CODE -> {

                val bitmap = ImageDecoder.createSource(context.contentResolver, selected!!).decodeBitmap { info, source -> val imageDecoder = this }

                base64 = encodeImageBitmap(bitmap)!!
            }
        }
        dataList.add(base64)

    }

    interface CreateAdView {

        fun showErrorInTitle()
        fun showErrorInDescription()
        fun showErrorInPrize()
        fun showErrorInImage(position: Int)
        fun showPopup(photoId: Int)
        fun setupFields()

        fun saveAd(newAd: ad)
        fun showError()
    }
}