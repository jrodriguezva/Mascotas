package es.architectcoders.mascotas.generate_ad

import android.content.Context
import android.content.Intent
import es.architectcoders.mascotas.model.ad

class CreateAdPresenter(var view: CreateAdView) {
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
        var imgPath=""
        if(requestCode==CreateAdFragment.IMAGE_PICK_CODE){
            imgPath=data?.data.toString()
        }
        else if(requestCode==CreateAdFragment.IMAGE_CAPTURE_CODE){
            imgPath= data?.extras.toString()
        }
        dataList.add(imgPath)
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