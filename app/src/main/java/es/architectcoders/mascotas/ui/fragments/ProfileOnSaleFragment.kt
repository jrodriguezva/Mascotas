package es.architectcoders.mascotas.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import es.architectcoders.mascotas.R


class ProfileOnSaleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_on_sale_fragment, container, false)
    }
}
