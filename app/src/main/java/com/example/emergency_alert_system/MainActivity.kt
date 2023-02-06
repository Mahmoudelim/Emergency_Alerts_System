package com.example.emergency_alert_system

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.emergency_alert_system.user.UserDachboard
import com.example.emergencyalertsystem.R

class MainActivity : AppCompatActivity() {
    private var LocationPermissionGranted = false
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    LocationPermissionGranted = true
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    LocationPermissionGranted = true
                }
                else -> {
                    // No location access granted.
                }
            }
        }

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        val btn: Button = findViewById(R.id.login_btn)
        //logout
        if (LocationPermissionGranted == true) {
            btn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {

                    val intent = Intent(this@MainActivity, UserDachboard::class.java)
                    startActivity(intent);

                }

            })
        }
    }
}