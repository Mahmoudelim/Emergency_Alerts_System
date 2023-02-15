package com.example.emergency_alert_system.EMP.creation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.emergency_alert_system.EMP.EmpDachboard
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth

class EP_login : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var EPemail_text: EditText
    lateinit var Eppassword_text: EditText
    lateinit var login_ep: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ep_login)
        mAuth=FirebaseAuth.getInstance()
        EPemail_text=findViewById(R.id.EP_editTextTextEmailAddress)
        Eppassword_text=findViewById(R.id.EP_editTextTextPassword)
        login_ep=findViewById(R.id.EP_log)
        login_ep.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val email=EPemail_text.text.toString().trim()
                val password=Eppassword_text.text.toString().trim()
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(this@EP_login,"success login EP", Toast.LENGTH_SHORT).show()
val intent= Intent(this@EP_login,EmpDachboard()::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@EP_login,"Erorr login EP"+ (task.exception!!.message.toString()), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}