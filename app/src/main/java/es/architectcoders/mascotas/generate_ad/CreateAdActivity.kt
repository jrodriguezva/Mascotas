package es.architectcoders.mascotas.generate_ad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R
import kotlinx.android.synthetic.main.activity_create_ad.*


class CreateAdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ad)
        supportFragmentManager.beginTransaction().replace(container.id,CreateAdFragment()).commit()
    }

}
