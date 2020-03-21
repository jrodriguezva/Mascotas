package es.architectcoders.mascotas.ui.advert

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.advert.fragment.AdvertDetailFragment
import kotlinx.android.synthetic.main.advertlist_activity.*

class AdvertDetailActivity : AppCompatActivity() {

    companion object {
        const val ADVERT_ID = "ADVERT_ID"
        fun newInstance(context: Context) = Intent(context, AdvertDetailActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advertlist_activity)
        setSupportActionBar(toolbarAdvert)
        if (savedInstanceState == null) {
            val value = intent.extras?.getString(ADVERT_ID) ?: ""
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AdvertDetailFragment.newInstance(value))
                .commitNow()
        }
    }
}