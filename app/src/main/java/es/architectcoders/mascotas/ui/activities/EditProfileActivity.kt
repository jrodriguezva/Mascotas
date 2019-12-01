package es.architectcoders.mascotas.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.routers.goToEditProfileActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*


class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(toolbarEditProfile)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        setSupportActionBar(toolbarEditProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        menuInflater.inflate(R.menu.menu_edit_profile, menu)

        toolbarEditProfile.setNavigationOnClickListener {
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