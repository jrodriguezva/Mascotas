package es.architectcoders.mascotas.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.fragments.EditProfileFragment
import kotlinx.android.synthetic.main.edit_profile_activity.*
import kotlinx.android.synthetic.main.edit_profile_fragment.*


class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerEditProfile, EditProfileFragment.newInstance())
                .commitNow()
        }
    }
}