package es.architectcoders.mascotas.generate_ad

class CreateAdPresenter(var view: CreateAdView) {
    fun onStart() {
        view.setupFields()
    }

    fun saveButtonPressed() {

    }

    fun photoImgPressed() {
        view.showPopup()
    }


    interface CreateAdView{

        fun showErrorInTitle()
        fun showErrorInDescription()
        fun showErrorInPrize()
        fun showErrorInImage(position:Int)
        fun showPopup()
        fun setupFields()

        fun saveDone()
        fun saveFail()
    }
}