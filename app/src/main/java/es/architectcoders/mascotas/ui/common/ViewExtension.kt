package es.architectcoders.mascotas.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

@BindingAdapter("load_url")
fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}