package es.architectcoders.mascotas.ui.advert

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.advert.fragment.AdvertListFragment
import es.architectcoders.mascotas.ui.advert.fragment.NewAdvertFragment
import kotlinx.android.synthetic.main.advertlist_activity.*

class NewAdvertActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, NewAdvertActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advertlist_activity)
        setSupportActionBar(toolbarAdvert)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewAdvertFragment.newInstance())
                .commitNow()
        }
    }
}