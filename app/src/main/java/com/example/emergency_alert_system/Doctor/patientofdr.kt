package com.example.emergency_alert_system.Doctor
import android.R
import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.emergency_alert_system.user.creation.user_midical_info
import com.example.emergency_alert_system.user.creation.user_vitaldata
import com.example.emergency_alert_system.user.model.medicine
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import org.checkerframework.common.reflection.qual.NewInstance


class patientofdr : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.emergencyalertsystem.R.layout.activity_patientofdr)
        val heartRateInput =
            findViewById<TextView>(com.example.emergencyalertsystem.R.id.heart_rate_input)
        heartRateInput.text = "100"

        val patientName = intent.getStringExtra("pat")
        val patientName2 = intent.getStringExtra("pat2")
        if (patientName != null) {
            getChronicConditionsForUser(patientName)
            getVitalDataForUser(patientName)
            getmedicine(patientName)
            val radio: RadioGroup =
                findViewById(com.example.emergencyalertsystem.R.id.severity_radio_group)
            radio.setOnCheckedChangeListener { group, checkedId ->
                val severity =
                    if (com.example.emergencyalertsystem.R.id.low_severity_radio_button == checkedId) "Normal" else if (com.example.emergencyalertsystem.R.id.medium_severity_radio_button == checkedId) "High" else "Very High"
                updateSeverityLevelForUser(severity)

                // Do something with the patient name
            }

        }
        if (patientName2 != null) {
            getChronicConditionsForUser(patientName2)
            getVitalDataForUser(patientName2)
            val radio: RadioGroup =
                findViewById(com.example.emergencyalertsystem.R.id.severity_radio_group)
            radio.setOnCheckedChangeListener { group, checkedId ->
                val severity =
                    if (com.example.emergencyalertsystem.R.id.low_severity_radio_button == checkedId) "Normal" else if (com.example.emergencyalertsystem.R.id.medium_severity_radio_button == checkedId) "High" else "Very High"
                updateSeverityLevelForUser(severity)
                getmedicine(patientName2)
                // Do something with the patient name
                Toast.makeText(this, "Patient name2: $patientName", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun getmedicine(username:String) {
        val db = FirebaseFirestore.getInstance()
        val docref= db.collection("/USERS MEDICAL INFO").document("/$username:medical info")

        docref.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->

            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@EventListener
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {

                docref.get().addOnSuccessListener { documentSnapshot ->
                    val userInfo = documentSnapshot.toObject(user_midical_info::class.java)
                    if (userInfo != null) {
                        userInfo.medicines!!.forEach {
                            // Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                            val medicineNames = ArrayList<String>()

                            for (medicine in userInfo.medicines!!) {
                                val medicineName = medicine.medicine_name
                                if (medicineName != null) {
                                    medicineNames.add(medicineName)
                                }
                            val chori =
                                findViewById<TextView>(com.example.emergencyalertsystem.R.id.medicine_text)
                            chori.text = medicineNames.toString()
                            Toast.makeText(this, "${userInfo.medicines}", Toast.LENGTH_SHORT).show()

                        }}
                    }
                }
            }
        })}
    // Retrieve vital data for a given user
    fun getVitalDataForUser(username: String) {

        val database = FirebaseDatabase.getInstance().getReference("Blood Oxygen (%)")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bloodOxygen = dataSnapshot.value
                val oxi =findViewById<TextView>(com.example.emergencyalertsystem.R.id.blood_oxygen_input)
                oxi.text = "${bloodOxygen}%"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })

        val database2 = FirebaseDatabase.getInstance().getReference("Heart Rate (BPM)")
        database2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val heartRate = dataSnapshot.value
                val heartrete = findViewById<TextView>(com.example.emergencyalertsystem.R.id.heart_rate_input)
                heartrete.text = "${heartRate}"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })

        val database3 = FirebaseDatabase.getInstance().getReference("Body Temperature (°C)")
        database3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bodyTemperature = dataSnapshot.value
                val temp = findViewById<TextView>(com.example.emergencyalertsystem.R.id.temperature_input)
                temp.text = "${bodyTemperature} °C"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
        val database4 = FirebaseDatabase.getInstance().getReference("severity")
        database3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bodyTemperature = dataSnapshot.value
                val temp = findViewById<TextView>(com.example.emergencyalertsystem.R.id.severity)
                temp.text = "${bodyTemperature} °C"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })


    }

    // Retrieve chronic conditions for a given user
    fun getChronicConditionsForUser(username: String) {
        val db = FirebaseFirestore.getInstance()
        val docref= db.collection("/USERS MEDICAL INFO").document("/$username:medical info")

        docref.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->

            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@EventListener
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {

                docref.get().addOnSuccessListener { documentSnapshot ->
                    val userInfo = documentSnapshot.toObject(user_midical_info::class.java)
                    if (userInfo != null) {
                        userInfo.choronic!!.forEach {
                            // Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                            val chori = findViewById<TextView>(com.example.emergencyalertsystem.R.id.chronic_condition_input)
                            chori.text=userInfo.choronic.toString()
                            Toast.makeText(this, "${userInfo.choronic}", Toast.LENGTH_SHORT).show()

                        }
                    }
                }

            }})}
    fun addMedicineForUser(username: String, medicine: medicine) {
        val db = FirebaseFirestore.getInstance()
        val docref= db.collection("/USERS MEDICAL INFO").document("/$username:medical info")
        docref.update("medicines", FieldValue.arrayUnion(medicine))
            .addOnSuccessListener {
                //Toast.makeText(, "done", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                //Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
            }
    }

    // Update the severity level for a given user
    fun updateSeverityLevelForUser( severityLevel: String) {
        val database = FirebaseDatabase.getInstance()
        val userVitalDataRef: DatabaseReference = database.getReference("severity")
        userVitalDataRef.setValue(severityLevel)
            .addOnSuccessListener {
                // The severity level was updated successfully
            }
            .addOnFailureListener { exception ->
                // Handle any errors
            }
    }
}