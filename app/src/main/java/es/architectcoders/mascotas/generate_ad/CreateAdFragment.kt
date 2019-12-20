package es.architectcoders.mascotas.generate_ad

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
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
import es.architectcoders.mascotas.model.ad
import kotlinx.android.synthetic.main.camera_input_layout.*
import kotlinx.android.synthetic.main.form_input_layout.*
import kotlinx.android.synthetic.main.fragment_create_ad.*
import org.jetbrains.anko.support.v4.alert


class CreateAdFragment : Fragment(), CreateAdPresenter.CreateAdView, View.OnClickListener {
    companion object {
        //image pick code
        internal const val IMAGE_PICK_CODE = 1000;
        //Permission code
        internal const val IMAGE_CAPTURE_CODE = 1002
        private const val ONE=1;
        private const val TWO=2;
        private const val THREE=3;
        private const val FOUR=4;
        private const val FIVE=5;
        private const val SIX=6

    }

    private var photoId: Int=0
    private lateinit var list: java.util.HashMap<Int, Int>

    private lateinit var presenter: CreateAdPresenter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment_create_ad, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = CreateAdPresenter(this)
        presenter.onStart()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val selectedImage = data?.data
        val cameraImage = data?.extras
        if (resultCode == Activity.RESULT_OK)
        {
            when (requestCode) {
                IMAGE_CAPTURE_CODE -> {
                    loadPhoto(cameraImage?.get("data") as Bitmap)
                }
                IMAGE_PICK_CODE -> {
                    loadPhoto(selectedImage!!)
                }
            }
            presenter.dataRecived(data, requestCode)
        }
    }

    private fun loadPhoto(selectedImage:Any?) {
        when (photoId) {
            ONE -> {
                Glide.with(context!!).load(selectedImage).into(imgOne)
            }
            TWO -> {
                Glide.with(context!!).load(selectedImage).into(imgTwo)
            }
            THREE -> {
                Glide.with(context!!).load(selectedImage).into(imgThree)
            }
            FOUR -> {
                Glide.with(context!!).load(selectedImage).into(imgFour)
            }
            FIVE -> {
                Glide.with(context!!).load(selectedImage).into(imgFive)
            }
            SIX -> {
                Glide.with(context!!).load(selectedImage).into(imgSix)
            }
        }
    }

    override fun onClick(v: View?) {
        when {
            v?.id!! == photoOne.id -> {
                presenter.clickedPhoto(ONE)
            }
            v.id == photoTwo.id -> {
                presenter.clickedPhoto(TWO)
            }
            v.id == photoThree.id -> {
                presenter.clickedPhoto(THREE)
            }
            v.id == photoFour.id -> {
                presenter.clickedPhoto(FOUR)
            }
            v.id == photoFive.id -> {
                presenter.clickedPhoto(FIVE)
            }
            v.id == photoSix.id -> {
                presenter.clickedPhoto(SIX)
            }
        }


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

    override fun showPopup(photoId: Int) {
        this.photoId=photoId
        alert("Choose an Option") {
            title = "Alert"
            positiveButton("CAMERA") { checkCameraPermission() }
            negativeButton("GALLERY") { checkGalleryPermission() }

        }.show()
    }


    override fun setupFields() {


        photoOne.setOnClickListener(this)
        photoTwo.setOnClickListener(this)
        photoThree.setOnClickListener(this)
        photoFour.setOnClickListener(this)
        photoFive.setOnClickListener(this)
        photoSix.setOnClickListener(this)

        customClick(currency)
        db = FirebaseFirestore.getInstance()
        testReadDB()
        currency.setOnClickListener { testWriteDB() }
        subirBtn.setOnClickListener {
        val newAd=ad(title?.text.toString(), description?.text.toString(), prize?.text.toString().toInt(),currency?.text.toString(),category?.text.toString(),ArrayList<String>())
            presenter.saveButtonPressed(newAd) }
    }

    override fun saveAd(newAd: ad) {
        // Create a new user with a first and last name
        // Create a new user with a first and last name
        val ad: MutableMap<String, Any> = HashMap()
        ad[getString(R.string.title)] = newAd.title
        ad[getString(R.string.description)] = newAd.description
        ad[getString(R.string.prize)] = newAd.prize
        ad[getString(R.string.currency)] = newAd.currency
        ad[getString(R.string.category)] = newAd.category
        ad[getString(R.string.images)] = newAd.imgUriList

        // Add a new document with a generated ID
        // Add a new document with a generated ID
        db.collection("ads")
            .add(ad)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "TAG",
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w("TAG", "Error adding document", e) }
    }

    override fun showError() = Toast.makeText(context,"Error in some field",Toast.LENGTH_SHORT).show()



    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                PRIVATE METHODS
 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

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
                ) {
                }
            }).check()
    }

    private fun checkGalleryPermission() {
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
                ) {
                }
            }).check()
    }

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            MOCK METHODS
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

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

    private fun testReadDB() {
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


}
