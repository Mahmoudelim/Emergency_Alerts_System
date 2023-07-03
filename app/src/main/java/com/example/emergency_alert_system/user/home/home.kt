package com.example.emergency_alert_system.user.home

import android.Manifest.permission.SCHEDULE_EXACT_ALARM
import android.app.AlertDialog
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Dialogesandmaps.EmergencyDialogFragment
import com.example.emergency_alert_system.notifications.AlertNotificationService.Companion.Alert_Channel_Id
import com.example.emergency_alert_system.notifications.MedicationReminderService
import com.example.emergency_alert_system.user.AlertMaking.MakeAlert

import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

import kotlinx.android.synthetic.main.fragment_home2.*
import java.util.*
import kotlin.collections.ArrayList


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
        val button = view.findViewById<Button>(R.id.alert1)
        val intent = Intent(requireContext(), MedicationReminderService::class.java)
        val oxi= view.findViewById<TextView>(R.id.oxi)
            val heartrete=view.findViewById<TextView>(R.id.heartRATE)
                val temp=view.findViewById<TextView>(R.id.body_temp)
        requireContext().startService(intent)
       /* val database = FirebaseDatabase.getInstance().getReference("Blood Oxygen (%)")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bloodOxygen = dataSnapshot.value as String
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
                val heartRate = dataSnapshot.value as String
                val heartrete = view.findViewById<TextView>(R.id.heartRATE)
                heartrete.text = "${heartRate} BPM"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        val database3 = FirebaseDatabase.getInstance().getReference("Body Temperature (°C)")
        database3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bodyTemperature = dataSnapshot.value as String
                val temp = view.findViewById<TextView>(R.id.body_temp)
                temp.text = "${bodyTemperature} °C"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        */


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
                    }

    }


}