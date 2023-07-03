package com.example.emergency_alert_system.user.model

import com.example.emergency_alert_system.Doctor.Creation.Doctor
import org.checkerframework.checker.units.qual.Time
import java.util.Timer

data class medicine( var medicine_name:String?=null, var medicine_time:MutableMap<String, Any>?=null,var assignedWith:String?=null)
