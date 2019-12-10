package es.architectcoders.mascotas.generate_ad

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.Utils.customClick
import kotlinx.android.synthetic.main.camera_input_layout.*
import kotlinx.android.synthetic.main.form_input_layout.*
import kotlinx.android.synthetic.main.fragment_create_ad.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.alert


/**
 * A placeholder fragment containing a simple view.
 */
class CreateAdActivityFragment : Fragment(), CreateAdPresenter.CreateAdView {
    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;

        private val IMAGE_CAPTURE_CODE =1002

    }
    private lateinit var presenter: CreateAdPresenter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView= inflater.inflate(R.layout.fragment_create_ad, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter=CreateAdPresenter(this)
        presenter.onStart()

    }

    private fun testWriteDB() {
// Create a new user with a first and last name
        // Create a new user with a first and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

// Add a new document with a generated ID
        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "TAG",
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w("TAG", "Error adding document", e) }
    }
    private fun testReadDB(){
        db.collection("users")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("TAG", document.id + " => " + document.data.get("born").toString())
                    }
                } else {
                    Log.w("TAG", "Error getting documents.", task.exception)
                }
            })
    }
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                CALLBACKS METHODS
 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/


    override fun showErrorInTitle() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorInDescription() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorInPrize() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorInImage(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPopup() {
        alert("Choose an Option") {
            title = "Alert"
            positiveButton("CAMERA") { checkCameraPermission()}
            negativeButton("GALLERY") { checkGalleryPermission()}

        }.show()
    }

    private fun checkCameraPermission() {
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    pickFromCamera()
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()
    }

    private fun checkGalleryPermission(){
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    pickFromGallery()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()
    }
    private fun pickFromCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }
    private fun pickFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun setupFields() {
        customClick(currency)
        db = FirebaseFirestore.getInstance()
        testReadDB()
        currency.setOnClickListener{testWriteDB()}
        subirBtn.setOnClickListener{presenter.saveButtonPressed()}
        subirBtn.setOnClickListener{presenter.saveButtonPressed()}
        cameraLinearLayout.setOnClickListener{presenter.photoImgPressed()}

    }

    override fun saveDone() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
           // image_view.setImageURI(image_uri)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

           // image_view.setImageURI(data?.data)
        }
    }
}
