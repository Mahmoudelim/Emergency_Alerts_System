package com.example.emergency_alert_system.user

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.emergency_alert_system.MainActivity
import com.example.emergencyalertsystem.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_dachboard.*
import kotlinx.android.synthetic.main.activity_user_dachboard.view.*
import kotlinx.android.synthetic.main.nav_header.*


class UserDachboard : AppCompatActivity()  {
    lateinit var appBarConfiguration: AppBarConfiguration
     lateinit var navController: NavController
     lateinit var drawerLayout: DrawerLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dachboard)
        val useremail = findViewById<TextView>(R.id.user_email_navview)
        val nusername = findViewById<TextView>(R.id.user_name_navview)

        var mAuth:FirebaseAuth
        var firestore:FirebaseFirestore
        mAuth= FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        val UID = mAuth.currentUser!!.uid
        val userref = firestore.collection("USERS").document(UID).get()
            .addOnSuccessListener { document ->
                val userName: String? = document.getString("username")
                val userEmail: String? = document.getString("email")
                Toast.makeText(this, "$userEmail and $userName", Toast.LENGTH_SHORT).show()
                     if (nusername !=null && useremail !=null) {
                         Log.i("uyuy", userName.toString())

                         nusername.text=userName
                         useremail.text=userEmail
                     }


            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting user document: ", exception)
            }
        navController=findNavController(R.id.user_nav_host_fragment)
        drawerLayout=findViewById(R.id.drawer_layout)
        nav_view.setupWithNavController(navController)
        appBarConfiguration= AppBarConfiguration(navController.graph,drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.user_nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()

    }


}



