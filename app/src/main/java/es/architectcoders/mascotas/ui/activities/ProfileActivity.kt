package es.architectcoders.mascotas.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import es.architectcoders.mascotas.ui.adapters.ProfilePagerAdapter
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.routers.goToEditProfileActivity
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbarProfile)

        val fragmentAdapter = ProfilePagerAdapter(supportFragmentManager)
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
            goToEditProfileActivity()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
