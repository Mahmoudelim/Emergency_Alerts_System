package com.example.emergency_alert_system.user.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Dialogesandmaps.EmergencyDialogFragment
import com.example.emergency_alert_system.user.AlertMaking.MakeAlert
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.android.synthetic.main.fragment_home2.*
import org.checkerframework.checker.nullness.qual.NonNull
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 */
class home : Fragment() {
    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView:RecyclerView
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
        return inflater.inflate(R.layout.fragment_home2, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val database = FirebaseDatabase.getInstance().getReference("Blood Oxygen (%)")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bloodOxygen = dataSnapshot.value
                val oxi = view.findViewById<TextView>(R.id.oxi)
                oxi.text = "${bloodOxygen}%"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        val database2 = FirebaseDatabase.getInstance().getReference("Heart Rate (BPM)")
        database2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val heartRate = dataSnapshot.value
                val heartrete = view.findViewById<TextView>(R.id.heartrate)
                heartrete.text = "${heartRate}"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        val database3 = FirebaseDatabase.getInstance().getReference("Body Temperature (°C)")
        database3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bodyTemperature = dataSnapshot.value
                val temp = view.findViewById<TextView>(R.id.bodytempretrure)
                temp.text = "${bodyTemperature} °C"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        checkSeverityAndAlert()
/*        button=view.findViewById(R.id.alert1)
        button.setOnClickListener {
            val dialog = EmergencyDialogFragment.newInstance()

            dialog.show(parentFragmentManager, "emergency_dialog")
            var mAuth: FirebaseAuth
            var firestore: FirebaseFirestore
            mAuth= FirebaseAuth.getInstance()
            firestore= FirebaseFirestore.getInstance()
            val UID =mAuth.currentUser!!.uid
            val userRef = firestore.collection("USERS").document(UID)
            userRef.get().addOnSuccessListener { document ->
                val nm: String? = document.data!!["username".trim()].toString()
                if (document != null && document.exists()) {
                    val relatives = document.get("relatives") as ArrayList<String>?
                    relatives?.forEach { name ->
                        firestore.collection("User_Token").whereEqualTo("user_name", name).get()
                            .addOnSuccessListener { documents ->
                                if (documents != null && !documents.isEmpty) {
                                    val token = documents.documents[0].get("fcm_token") as String?
                                    if (token !=null){

                                        val message = RemoteMessage.Builder(token)
                                            .setMessageId(UUID.randomUUID().toString())
                                            .setData(mapOf("username" to "$nm in emergency situation"))

                                            .build()

// Send the notification message
                                        FirebaseMessaging.getInstance().send(message)}

                                }
                            }.addOnFailureListener { exception ->
                                // Handle any errors
                            }
                    }
                } else {
                    // Document does not exist
                }
            }.addOnFailureListener { exception ->
                // Handle any errors
            }
        }*/

    }
    fun checkSeverityAndAlert() {
        val severityRef = FirebaseDatabase.getInstance().getReference("severity")

        severityRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val severity = dataSnapshot.getValue(String::class.java)
                val Alert=MakeAlert()
                if (severity == "Very High") {
                    val dialog = EmergencyDialogFragment.newInstance()
                    dialog.show(parentFragmentManager, "emergency_dialog")
                } else if (severity == "High") {
                    Alert.AlertToDoc()
                    Alert.AlertToRelatives()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }



}