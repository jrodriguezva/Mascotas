package es.architectcoders.mascotas.ui.profileEdit.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.profileEdit.fragments.ProfileEditFragment
import kotlinx.android.synthetic.main.edit_profile_activity.*


class ProfileEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_activity)
        setSupportActionBar(toolbarProfileEdit)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerEditProfile, ProfileEditFragment.newInstance())
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

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.btnSave) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}