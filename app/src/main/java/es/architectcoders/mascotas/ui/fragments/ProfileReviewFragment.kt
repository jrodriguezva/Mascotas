package es.architectcoders.mascotas.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import es.architectcoders.mascotas.R


class ProfileReviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_review, container, false)
    }
}
