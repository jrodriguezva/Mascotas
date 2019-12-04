package es.architectcoders.mascotas.generate_ad

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.R

import kotlinx.android.synthetic.main.activity_create_ad.*

class CreateAdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ad)
        supportFragmentManager.beginTransaction().replace(container.id,CreateAdActivityFragment()).commit()
    }

}
