package es.architectcoders.mascotas.ui.common

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, Observer(body))
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility =
        if(visible == true) {
            View.VISIBLE
        }
        else {
            View.GONE
        }
}
