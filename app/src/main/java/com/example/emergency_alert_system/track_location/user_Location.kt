package com.example.emergency_alert_system.track_location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface user_Location {
    fun getLocationUpdate(interval : Long): Flow<Location>
    class LocationException(message: String):Exception()
}