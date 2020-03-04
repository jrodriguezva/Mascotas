package es.architectcoders.mascotas.ui.common

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import es.architectcoders.mascotas.model.MyFirebaseAdvert
import es.architectcoders.mascotas.ui.advertlist.AdvertsAdapter

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility =
        if (visible == true) {
            View.VISIBLE
        } else {
            View.GONE
        }
}

@BindingAdapter("error_msg")
fun TextInputLayout.setErrorMsg(msg: String?) {
    error = msg
}
