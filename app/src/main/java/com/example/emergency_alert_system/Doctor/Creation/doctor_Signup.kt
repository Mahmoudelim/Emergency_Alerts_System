package com.example.emergency_alert_system.Doctor.Creation

import android.os.Bundle
import android.view.View
import android.view.View.inflate
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.emergency_alert_system.MIddle_Layer.CRUD_operations
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore


class doctor_Signup : AppCompatActivity() {
//edittext
    lateinit var doctorname_text:EditText
    lateinit var docemail_text:EditText
    lateinit var doc_special :EditText
    lateinit var doc_quali :EditText
    lateinit var doc_pass :EditText

    //Buttons
    lateinit var signup_btn:Button
    var db = FirebaseFirestore.getInstance()
    private lateinit var mAuth: FirebaseAuth
var dooc:Doctor=Doctor()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = doctor_Signup.inflate(layoutInflater)
        //val view = binding.root
        setContentView(R.layout.activity_signup)
        signup_btn=findViewById(R.id.doc_signup)
        doctorname_text=findViewById(R.id.doc_name)
        docemail_text=findViewById(R.id.doc_Email)
        doc_special=findViewById(R.id.doc_specialization)
        doc_quali=findViewById(R.id.doc_qualification)
        doc_pass=findViewById(R.id.password)
        //val create_doctor :CRUD_operations? =null
        mAuth=FirebaseAuth.getInstance()

        signup_btn.setOnClickListener{

                val doctorname=doctorname_text.text.toString()
                val email=docemail_text.text.toString()
                val  specilization=doc_special.text.toString()
                val   qualification=doc_quali.text.toString()
                val   password=doc_pass.text.toString()
            dooc.doctorname = doctorname
            dooc.email = email
            dooc.specilization = specilization
            dooc.qualification= qualification
            dooc.password = password


            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                if (task.isSuccessful){
                val doctors= db.collection("Doctor")
                doctors.document(doctorname).set(dooc)
                    .addOnSuccessListener {
                        Toast.makeText(this@doctor_Signup,"successfuly added the doc", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this@doctor_Signup,"failed  added doc", Toast.LENGTH_SHORT).show()
                    }

            }
                else {
                    Toast.makeText(this@doctor_Signup,"erroe auth EP"+ (task.exception!!.message.toString()), Toast.LENGTH_SHORT).show()

                }


      //  var doctor=Doctor(doctorname,email,specilization,
        //    qualification,password)

     // create_doctor!!.create_doctor( signup_btn,doctor)
    }

}}}