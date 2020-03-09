package es.architectcoders.mascotas.ui.profile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import es.architectcoders.mascotas.ui.profile.adapters.ProfilePageAdapter
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.Event
import es.architectcoders.mascotas.ui.profileEdit.activities.ProfileEditActivity
import kotlinx.android.synthetic.main.profile_activity.*
import es.architectcoders.mascotas.ui.common.startActivity


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        setSupportActionBar(toolbarProfile)

        val fragmentAdapter = ProfilePageAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)

        photo.setOnClickListener {}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        setSupportActionBar(toolbarProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        menuInflater.inflate(R.menu.menu_profile, menu)

        toolbarProfile.setNavigationOnClickListener {
            finish()
        }
        return true
    }


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.btnEdit) {
            navigateToEditProfile()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun navigateToEditProfile() {
        startActivity<ProfileEditActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { finish() }
    }
}
