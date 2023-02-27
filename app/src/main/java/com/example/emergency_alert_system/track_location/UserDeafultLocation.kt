package com.example.emergency_alert_system.track_location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationSettingsRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class UserDeafultLocation(
    private val context: Context,
    private val client:FusedLocationProviderClient
):user_Location {
    @SuppressLint("MissingPermission")
    override fun getLocationUpdate(interval: Long): Flow<Location> {
        return callbackFlow {
            if (!context.hasLocationPermission()){
                 throw user_Location.LocationException("Missing Location Permission")
                }
            val locationManager=context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnapled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (! isGpsEnapled && ! isNetworkEnabled)
            {
                throw user_Location.LocationException("Gps is disabled")
            }
            val request = com.google.android.gms.location.LocationRequest.create().setInterval(interval)
                .setFastestInterval(interval)
            val locationCallback=object :LocationCallback(){
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch {
                            send(location)
                        }
                    }
                }

            }
            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
            awaitClose{
                client.removeLocationUpdates(locationCallback)

            }
        }
    }
}