package es.architectcoders.mascotas.generate_ad

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import es.architectcoders.mascotas.R
import es.architectcoders.mascotas.Utils.customClick
import kotlinx.android.synthetic.main.form_input_layout.*


/**
 * A placeholder fragment containing a simple view.
 */
class CreateAdActivityFragment : Fragment() {

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
        customClick(currency)
         db = FirebaseFirestore.getInstance()
        testReadDB()
        currency.setOnClickListener{testWriteDB()}
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
}
