package com.example.emergency_alert_system.MIddle_Layer

import com.example.emergency_alert_system.MIddle_Layer.GET_AND_POST
import com.example.emergency_alert_system.user.creation.user_general_info
import com.example.emergency_alert_system.user.creation.user_location
import com.example.emergency_alert_system.user.creation.user_midical_info
import com.google.firebase.firestore.FirebaseFirestore


class Emergency_alert {

     fun get_usermedical_info(username:String?=null): user_midical_info {
        var db = FirebaseFirestore.getInstance()
        var user_medical: user_midical_info = user_midical_info()
        val users = db.collection("Users").document("${username}:medical info ".trim())
            .get().addOnSuccessListener { documentSnapshot ->
                user_medical = documentSnapshot.toObject(user_midical_info::class.java)!!
            }

        return user_medical

    }
     fun get_usergeneral_info(username:String?=null): user_general_info{
        var db = FirebaseFirestore.getInstance()
        var user_general:user_general_info= user_general_info()
        val users = db.collection("Users").document(username!!)
            .get().addOnSuccessListener { documentSnapshot ->
                user_general = documentSnapshot.toObject(user_general_info::class.java)!!
            }
        return user_general
    }
     fun get_useraddress_info(username:String?=null): user_location{
        var db = FirebaseFirestore.getInstance()
        var user_address:user_location= user_location()
        val users = db.collection("Users").document("${username}:Address info ".trim())
            .get().addOnSuccessListener { documentSnapshot ->
                user_address = documentSnapshot.toObject(user_location::class.java)!!
            }
        return user_address
    }

    private fun get_emergencycase(username:String?=null ): String {
//on data chnge listner
// first  add user.emergency precentage
        var user:user_general_info= user_general_info()
        var db = FirebaseFirestore.getInstance()
        val users = db.collection("Emergency_cases").document(username!!) .get().addOnSuccessListener { documentSnapshot ->
          user = documentSnapshot.toObject(user_general_info::class.java)!!
        }
 //after login
    return user.username!!
    }

    fun get_user_lastreport(username:String) {

    }

    fun neaarestep(){}

}