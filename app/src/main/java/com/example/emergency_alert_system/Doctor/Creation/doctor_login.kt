package com.example.emergency_alert_system.Doctor.Creation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.emergency_alert_system.Doctor.DoctorDachboard
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth

class doctor_login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var doctoremail_text: EditText
    lateinit var doctorpassword_text: EditText
    lateinit var login_doctor: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_login)
        mAuth=FirebaseAuth.getInstance()
        login_doctor=findViewById(R.id.doc_log)
        doctoremail_text=findViewById(R.id.doc_editTextTextEmailAddress)
        doctorpassword_text=findViewById( R.id.doc_editTextTextPassword)
        login_doctor.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val email=doctoremail_text.text.toString().trim()
                val password=doctorpassword_text.text.toString().trim()
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(this@doctor_login,"success login doc", Toast.LENGTH_SHORT).show()
val intent= Intent(this@doctor_login,DoctorDachboard().javaClass)
                         startActivity(intent)
                       finish()
                    }
                    else{
                        Toast.makeText(this@doctor_login,"erroe login doc"+ (task.exception!!.message.toString()), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}