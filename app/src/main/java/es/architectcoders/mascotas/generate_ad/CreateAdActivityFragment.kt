package es.architectcoders.mascotas.generate_ad

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.Utils.CustomEditText
import es.architectcoders.mascotas.Utils.customClick
import kotlinx.android.synthetic.main.form_input_layout.*

/**
 * A placeholder fragment containing a simple view.
 */
class CreateAdActivityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView= inflater.inflate(R.layout.fragment_create_ad, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customClick(currency)

    }
}
