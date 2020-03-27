package es.architectcoders.mascotas.ui.profile.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.profile.fragments.EditProfileFragment
import kotlinx.android.synthetic.main.edit_profile_activity.*


class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_activity)
        setSupportActionBar(toolbarProfileEdit)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerEditProfile, EditProfileFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        setSupportActionBar(toolbarProfileEdit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.edit_profile)
        menuInflater.inflate(R.menu.menu_profile_edit, menu)

        toolbarProfileEdit.setNavigationOnClickListener {
            finish()
        }
        return true
    }
}