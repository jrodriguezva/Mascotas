package es.architectcoders.mascotas.ui.routers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import es.architectcoders.mascotas.ui.activities.EditProfileActivity
import es.architectcoders.mascotas.ui.activities.ProfileActivity


fun AppCompatActivity.goToEditProfileActivity(){
    val intent = Intent(this, EditProfileActivity::class.java)
    this.startActivity(intent)
}
fun AppCompatActivity.goToProfileActivity(){
    val intent = Intent(this, ProfileActivity::class.java)
    this.startActivity(intent)
}