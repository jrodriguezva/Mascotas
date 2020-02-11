package es.architectcoders.mascotas.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.viewmodel.EditProfileViewModel
import kotlinx.android.synthetic.main.edit_profile_activity.*

class EditProfileFragment: Fragment(){
    companion object {
        fun newInstance() = EditProfileFragment()
    }

    private lateinit var viewModel: EditProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_profile_fragment, containerEditProfile, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       /*
        viewModel = withViewModel({ LoginViewModel(EditProfileRepository(FirebaseAuth.getInstance())) }) {
            observe(model, ::updateUI)
        }

        updateUserButton.setOnClickListener {
            viewModel.updateUser(
                nameEdit.text.toString(),
                surnameEdit.text.toString(),
                addressEdit.text.toString(),
                postalAddressEdit.text.toString(),
                emailEdit.text.toString())
        }
        */
    }

}