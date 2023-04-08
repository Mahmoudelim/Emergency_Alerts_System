package com.example.emergency_alert_system.user.creation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.emergency_alert_system.track_location.LocationService
import com.example.emergency_alert_system.user.UserDachboard
import com.example.emergency_alert_system.user.home.home
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_map.*

class user_Login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var useremail_text: EditText
     var nm:String="recent uSer"

    lateinit var userpassword_text:EditText
    lateinit var login_user: Button

    var db = FirebaseFirestore.getInstance()
    lateinit var login_user2: Button
    lateinit var name:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        mAuth=FirebaseAuth.getInstance()


        login_user=findViewById(R.id.user_log)

       useremail_text=findViewById(R.id.user_editTextTextEmailAddress)
        userpassword_text=findViewById( R.id.user_editTextTextPassword)


        login_user.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                val email=useremail_text.text.toString().trim()
                val password=userpassword_text.text.toString().trim()
              //  if (email=="a"&&password=="a"){
                   // val intent = Intent(this, map::class.java)
                    //startActivity(intent)
               // }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){


                     val intent2=Intent(applicationContext,LocationService::class.java).apply { action=LocationService.ActionStart
                         startService(this)

                     }

val intent=Intent(this@user_Login,UserDachboard().javaClass)
                       startActivity(intent)

                        Intent(applicationContext, LocationService::class.java).apply {
                            action= LocationService.ActionStart
                            startService(this)
                        }
                        finish()
                    }
                    else{
                        Toast.makeText(this@user_Login,"Error login user"+ (task.exception!!.message.toString()), Toast.LENGTH_SHORT).show()
                    }
                    }
                }}



        )}


    fun  getcurrentuser():String{

        mAuth=FirebaseAuth.getInstance()
        val UID =mAuth.currentUser!!.uid
        val docref=db.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->
            if (document !=null){
                nm=document.data!!["username".trim()].toString()

            }
        }
            .addOnFailureListener { exception ->
                //   Toast(this).show()

            }



        return nm

    }

}












