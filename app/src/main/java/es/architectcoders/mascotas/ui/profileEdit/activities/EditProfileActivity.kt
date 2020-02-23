package es.architectcoders.mascotas.ui.profileEdit.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.profileEdit.fragments.ProfileEditFragment


class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerEditProfile, ProfileEditFragment.newInstance())
                .commitNow()
        }
    }
}