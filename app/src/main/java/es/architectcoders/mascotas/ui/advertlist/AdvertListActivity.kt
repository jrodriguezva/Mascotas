package es.architectcoders.mascotas.ui.advertlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.advertlist.fragment.AdvertListFragment
import es.architectcoders.mascotas.ui.routers.goToProfileActivity
import kotlinx.android.synthetic.main.advertlist_activity.*

class AdvertListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advertlist_activity)
        setSupportActionBar(toolbarAdvertlist)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AdvertListFragment.newInstance())
                .commitNow()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        setSupportActionBar(toolbarAdvertlist)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.app_name)
        menuInflater.inflate(R.menu.menu_advertlist, menu)

        toolbarAdvertlist.setNavigationOnClickListener {
            finish()
        }
        return true
    }


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.btnProfile) {
            goToProfileActivity()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}