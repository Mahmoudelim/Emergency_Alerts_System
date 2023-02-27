package com.example.emergency_alert_system.track_location

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.emergencyalertsystem.Manifest

fun Context.hasLocationPermission():Boolean{
    return ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )== PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )== PackageManager.PERMISSION_GRANTED
}