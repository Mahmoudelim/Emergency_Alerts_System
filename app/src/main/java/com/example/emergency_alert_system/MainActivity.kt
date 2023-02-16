package com.example.emergency_alert_system

import android.Manifest
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.emergency_alert_system.Doctor.Creation.doctor_Signup
import com.example.emergency_alert_system.Doctor.Creation.doctor_login
import com.example.emergency_alert_system.EMP.creation.EP_login
import com.example.emergency_alert_system.EMP.creation.ep_Signup
import com.example.emergency_alert_system.notifications.AlertNotificationService
import com.example.emergency_alert_system.user.UserDachboard
import com.example.emergency_alert_system.user.creation.user
import com.example.emergency_alert_system.user.creation.user_Login
import com.example.emergency_alert_system.user.creation.user_signup
import com.example.emergency_alert_system.user.model.Alert
import com.example.emergencyalertsystem.R
import com.google.android.filament.Box
//import com.firebase.ui.auth.AuthUI
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder.Permission
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

import java.util.*

class MainActivity : AppCompatActivity() {
    private var LocationPermissionGranted = false
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToNext()

    }

        /*
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(object:PermissionListener{

            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
             goToNext()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(this@MainActivity,"You Must accept this permission",Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                TODO("Not yet implemented")
            }
        }).check()
    }


         */
    private fun goToNext() {
            val service=AlertNotificationService(applicationContext)
        val signupBtn:Button=findViewById(R.id.register_btn)
        val btn: Button = findViewById(R.id.login_btn)
        val radio :RadioGroup=findViewById(R.id.radio_group)
        radio.setOnCheckedChangeListener { group, checkedId ->
            val text =   if (R.id.user_radio == checkedId) "user" else if(R.id.doc_radio==checkedId)"doctor" else "emergency point"
            btn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    if (text=="user") {
                        val intent = Intent(this@MainActivity, user_Login::class.java)
                        startActivity(intent);
                        service.showNotification(
                            user("mahmoud"),
                            Alert("critical","his heart rate in dangerous case"))


                    }
                    else if (text=="doctor")
                    {
                        val intent = Intent(this@MainActivity, doctor_login::class.java)
                        startActivity(intent);
                    }
                    else if (text=="emergency point")
                    {
                        val intent = Intent(this@MainActivity, EP_login::class.java)
                        startActivity(intent);
                    }
                }

            })
            signupBtn.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {

                    if (text=="user") {
                        val intent = Intent(this@MainActivity, user_signup::class.java)
                        startActivity(intent);
                    }
                    else if (text=="doctor")
                    {
                        val intent = Intent(this@MainActivity, doctor_Signup::class.java)
                        startActivity(intent);
                    }
                    else if(text=="emergency point")
                    {
                        val intent = Intent(this@MainActivity, ep_Signup::class.java)
                        startActivity(intent);
                    }

                }

            })
        }


    }

}
