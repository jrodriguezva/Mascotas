package es.architectcoders.mascotas.ui.profileEdit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.profileEdit.viewmodel.ProfileEditViewModel
import kotlinx.android.synthetic.main.edit_profile_activity.*

class ProfileEditFragment: Fragment(){
    companion object {
        fun newInstance() =
            ProfileEditFragment()
    }

    private lateinit var editViewModel: ProfileEditViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_profile_fragment, containerEditProfile, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       /*
        editViewModel = withViewModel({ LoginViewModel(EditProfileRepository(FirebaseAuth.getInstance())) }) {
            observe(model, ::updateUI)
        }

        updateUserButton.setOnClickListener {
            editViewModel.updateUser(
                nameEdit.text.toString(),
                surnameEdit.text.toString(),
                addressEdit.text.toString(),
                postalAddressEdit.text.toString(),
                emailEdit.text.toString())
        }
        */
    }

}