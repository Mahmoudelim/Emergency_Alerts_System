package com.example.emergency_alert_system.user.creation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.emergency_alert_system.track_location.LocationService
import com.example.emergency_alert_system.user.UserDachboard
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
       /* val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val rememberMeCheckBox = findViewById<CheckBox>(R.id.rememberMe)
        val emailEditText = findViewById<EditText>(R.id.user_editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.user_editTextTextPassword)

// Load saved email and password from shared preferences
        val savedEmail = sharedPref.getString("email", "")
        val savedPassword = sharedPref.getString("password", "")

        emailEditText.setText(savedEmail)
        passwordEditText.setText(savedPassword)

        rememberMeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("rememberMe", isChecked).apply()
        }
*/
        login_user=findViewById(R.id.user_log)

       useremail_text=findViewById(R.id.user_editTextTextEmailAddress)
        userpassword_text=findViewById( R.id.user_editTextTextPassword)


        login_user.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val email=useremail_text.text.toString().trim()
                val password=userpassword_text.text.toString().trim()
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        getcurrentuser()
                         val currentuser=getcurrentuser()
                       /* val rememberMe = sharedPref.getBoolean("rememberMe", false)
                        if (rememberMe) {
                            sharedPref.edit().putString("email", emailEditText.text.toString()).apply()
                            sharedPref.edit().putString("password", passwordEditText.text.toString()).apply()
                        }*/
                        Toast.makeText(this@user_Login," ${currentuser.toString()} ",Toast.LENGTH_SHORT).show()
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












