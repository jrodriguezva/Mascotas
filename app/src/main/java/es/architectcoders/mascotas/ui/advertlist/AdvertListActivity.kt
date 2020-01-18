package es.architectcoders.mascotas.ui.advertlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.advertlist.fragment.AdvertListFragment

class AdvertListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advertlist_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AdvertListFragment.newInstance())
                .commitNow()
        }
    }
}