package es.architectcoders.mascotas.ui.common

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("invisible")
fun View.setInvisible(invisible: Boolean?) {
    visibility = if (invisible == true) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("error_msg")
fun TextInputLayout.setErrorMsg(msg: String?) {
    error = msg
}
