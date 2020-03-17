package es.architectcoders.mascotas.ui.advert

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.advert.fragment.AdvertListFragment
import es.architectcoders.mascotas.ui.common.startActivity
import es.architectcoders.mascotas.ui.profile.activities.ProfileActivity
import kotlinx.android.synthetic.main.advertlist_activity.*

class AdvertListActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, AdvertListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advertlist_activity)
        setSupportActionBar(toolbarAdvert)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AdvertListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        setSupportActionBar(toolbarAdvert)
        supportActionBar?.title = getString(R.string.app_name)
        menuInflater.inflate(R.menu.menu_advertlist, menu)

        toolbarAdvert.setNavigationOnClickListener {
            finish()
        }
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.btnProfile) {
            navigateToProfile()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun navigateToProfile() {
        startActivity<ProfileActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}