package com.example.emergency_alert_system.user.creation

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.emergency_alert_system.track_location.LocationService
import com.example.emergency_alert_system.user.UserDachboard
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class user_Login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var useremail_text: EditText
     var nm:String="recent uSer"

    lateinit var userpassword_text:EditText
    lateinit var login_user: Button
    lateinit var mAuth2: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    var db = FirebaseFirestore.getInstance()
    lateinit var login_user2: Button
    lateinit var name:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        mAuth=FirebaseAuth.getInstance()
        mAuth2= FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
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

                       /* val rememberMe = sharedPref.getBoolean("rememberMe", false)
                        if (rememberMe) {
                            sharedPref.edit().putString("email", emailEditText.text.toString()).apply()
                            sharedPref.edit().putString("password", passwordEditText.text.toString()).apply()
                        }*/
                        mAuth2= FirebaseAuth.getInstance()
                        firestore=FirebaseFirestore.getInstance()
                        val UID =mAuth.currentUser!!.uid
                        val userref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->

                            val nm: String? = document.data!!["username".trim()].toString()

                            val db = FirebaseFirestore.getInstance()
                            val docref = db.collection("/USERS MEDICAL INFO").document("/$nm:medical info")
                            docref.get().addOnSuccessListener { documentSnapshot ->


                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                    docref.get().addOnSuccessListener { documentSnapshot ->
                                        val userInfo = documentSnapshot.toObject(user_midical_info::class.java)
                                        if (userInfo != null) {
                                            userInfo.medicines!!.forEach {
                                                val medicineTime = it.medicine_time
                                                val medicineTimeList = mutableListOf<Map<String, Any>>()
                                                if (medicineTime != null) {
                                                    medicineTimeList!!.add(medicineTime)
                                                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener { task ->
                                                        if (task.isSuccessful) {
                                                            val token = task.result
                                                            if (token != null) {
                                                                // Store the token, medicine name, and time in Firestore
                                                                val medicineAlarm = hashMapOf(
                                                                    "medicine_name" to it.medicine_name,
                                                                    "medicine_time" to it.medicine_time,
                                                                    "fcm_token" to token
                                                                )
                                                                firestore.collection("medicine_alarms").document().set(medicineAlarm)
                                                                    .addOnSuccessListener {
                                                                        Log.d(TAG, "Medicine alarm added to Firestore")
                                                                    }
                                                                    .addOnFailureListener { e ->
                                                                        Log.e(TAG, "Error adding medicine alarm to Firestore", e)
                                                                    }
                                                            }
                                                        } else {
                                                            Log.e(TAG, "Error getting FCM token", task.exception)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }.addOnFailureListener { e ->
                                        Log.e(ContentValues.TAG, "Error retrieving medical info for user $nm from Firestore", e)
                                    }
                                } else {
                                    Log.e(ContentValues.TAG, "User $nm does not have medical info in Firestore")
                                }


                            }
                        }




                  //      Toast.makeText(this@user_Login," ${currentuser.toString()} ",Toast.LENGTH_SHORT).show()
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





}












