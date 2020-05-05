package es.architectcoders.mascotas.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.ui.login.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }
}
