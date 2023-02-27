package com.example.emergency_alert_system.user.AlertMaking

import com.example.emergency_alert_system.user.creation.user_Login
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class MakeAlert {
    //login user name
    val user :String = user_Login().getcurrentuser()
    var firestore:FirebaseFirestore=FirebaseFirestore.getInstance()
    //get user location from fireStore
    fun getuserlocation(){}
    fun findNearestEP(){}
    fun AlertToEp(){}
    fun AlertToRelatives(){}
    fun AlertToDoc(){}
}