package es.architectcoders.mascotas.ui.advertlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.advertlist.fragment.AdvertListFragment
import es.architectcoders.mascotas.ui.login.fragment.LoginFragment

class AdvertListActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context,AdvertListActivity::class.java)
    }

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