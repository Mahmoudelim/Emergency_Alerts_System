package com.example.emergency_alert_system.EMP.RequestAcceptance

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.emergency_alert_system.user.AlertMaking.nm
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_signup.*
import kotlinx.android.synthetic.main.fragment_request_acceptance.*
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RequestAcceptance.newInstance] factory method to
 * create an instance of this fragment.
 */

var myArray = ArrayList<String>()
var relativesPhone = ArrayList<String>()
class RequestAcceptance : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val firestore:FirebaseFirestore=FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_acceptance, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RequestAcceptance.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestAcceptance().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var AlertName =view.findViewById<TextView>(R.id.patientname)
        var street_name =view.findViewById<TextView>(R.id.street_name)
        var relative1 =view.findViewById<TextView>(R.id.relativeName1)
        var relative2 =view.findViewById<TextView>(R.id.relativesName2)
        var relative3 =view.findViewById<TextView>(R.id.relative3)
        var relativePhone1 =view.findViewById<TextView>(R.id.phone1)
        var relativePhone2 =view.findViewById<TextView>(R.id.phone2)
        var relativePhone3 =view.findViewById<TextView>(R.id.phonenumber)
        var neighborhood1 =view.findViewById<TextView>(R.id.neighborhoodtxt)
        var buildingNumber=view.findViewById<TextView>(R.id.building_numbertxt)

        val userName :String=requireArguments().getString("userName".trim()).toString()
        val collectionRef =firestore.collection("USERS")

// Query for documents that contain the "myField" field
        val query = collectionRef.whereEqualTo("username", userName)

        query.get().addOnSuccessListener { querySnapshot ->
            // Loop through the matching documents
            for (documentSnapshot in querySnapshot.documents) {
                // Get the array field from the document
                 myArray = documentSnapshot.get("relatives") as ArrayList<String>
                Toast.makeText(context, "Array from document ${documentSnapshot.id}: $myArray", Toast.LENGTH_SHORT).show()
                 relativesPhone = documentSnapshot.get("relativesphonenum") as ArrayList<String>
                relative1.setText(myArray[0])
                relative2.setText(myArray[1])
                relative3.setText(myArray[2])
                relativePhone1.setText(relativesPhone[0])
                relativePhone2.setText(relativesPhone[1])
                relativePhone3.setText(relativesPhone[2])
                // Do something with the array
                Log.d("cccc", "Array from document ${documentSnapshot.id}: $myArray")
                Log.d("eeee", "Array from document ${documentSnapshot.id}: $relativesPhone")
            }
        }.addOnFailureListener { e ->
            // Handle errors
            Log.w("MyApp", "Error getting documents.", e)
        }
        val LocationRef= firestore.collection("USERS Adresses")
        val q = LocationRef.whereEqualTo("username", userName)
        q.get().addOnSuccessListener { querySnapshot ->
            Log.i("snapshooot",querySnapshot.documents.toString())
            // Loop through the matching documents
            for (documentSnapshot in querySnapshot.documents) {
                val neighborhood=documentSnapshot.get("naighbourrhood")
                val streetName=documentSnapshot.get("streetname")
                Log.i("nenene",neighborhood.toString())
                val buildingNumber1=documentSnapshot.get("buildingnumb")
                street_name.setText(streetName.toString())
                neighborhood1.setText(neighborhood.toString())
                buildingNumber.setText(buildingNumber1.toString())
            }
        }


        val userStreet :String=requireArguments().getString("street name".trim()).toString()
        Toast.makeText(context,"$userName",Toast.LENGTH_SHORT).show()

        AlertName.setText(userName)

        Log.d("cccccc", "Array from document: $myArray")
        Log.d("eeee", "Array from document : $relativesPhone")


        val button = view.findViewById<Button>(R.id.toMedical)
        button.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val bundle=Bundle()
                bundle.putString("username",userName)
                v?.findNavController()?.navigate(R.id.action_requestAcceptance_to_medicalInfoForAcceptance,bundle)


    }})}}
