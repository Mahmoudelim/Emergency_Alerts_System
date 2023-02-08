package com.example.emergency_alert_system.Doctor.Creation

import android.os.Bundle
import android.view.View.inflate
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import com.example.emergencyalertsystem.R

import com.google.firebase.firestore.FirebaseFirestore


class doctor_Signup : AppCompatActivity() {
    private lateinit var binding: doctor_Signup


   // var signupbtn = findViewById(R.id.)as Button
    var db = FirebaseFirestore.getInstance()
    //lateinit var doctorname: String
    lateinit var email: String

    lateinit var specilization: String
    lateinit var qualification:String

    lateinit var password : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = doctor_Signup.inflate(layoutInflater)
        //val view = binding.root
        setContentView(R.layout.activity_signup)

      //  val doctor=Doctor(doctorname,email,specilization,
        //    qualification,password)


    }
    /*
    db.collection("Doctor")
    .add(doctor)
    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
        }
    })
    .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.w(TAG, "Error adding document", e);
        }
    });

     */
}