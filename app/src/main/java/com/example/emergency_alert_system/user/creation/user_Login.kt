package com.example.emergency_alert_system.user.creation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.emergency_alert_system.user.home.home
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth

class user_Login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var useremail_text: EditText
    lateinit var userpassword_text:EditText
    lateinit var login_user: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login2)
        login_user=findViewById(R.id.user_log)
       useremail_text=findViewById(R.id.user_editTextTextEmailAddress)
        userpassword_text=findViewById( R.id.user_editTextTextPassword)
        login_user.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val email=useremail_text.text.toString().trim()
                val password=userpassword_text.text.toString().trim()
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(this@user_Login,"success login user",Toast.LENGTH_SHORT).show()
//val intent=Intent(this@user_Login,home)
                       // startActivity(intent)
//finish()
                    }
                    else{
                        Toast.makeText(this@user_Login,"erroe login user"+ (task.exception!!.message.toString()), Toast.LENGTH_SHORT).show()
                    }
                    }
                }
    })

}}