package com.example.emergency_alert_system.MIddle_Layer


import com.example.emergency_alert_system.MIddle_Layer.GET_AND_POST
import com.example.emergency_alert_system.user.creation.user_midical_info
import com.example.emergency_alert_system.user.model.medicine
import com.google.firebase.firestore.FirebaseFirestore

class Mid_emergency_alert(){
 // scnario is if activated
    //viewing report
    //viewing all medical info of user  to doc
    //  write amedicin

    fun medicine_recomendation(){
get_usermedical_info()
add_medicine()

    }

    private fun add_medicine( username:String?=null,medicinename:String?=null,medicine_time:MutableMap<String, Any>?=null ,doc:String?=null) {
        var db = FirebaseFirestore.getInstance()
        val medicines: MutableList<medicine>?= mutableListOf()
        val medicine:medicine=medicine()
medicine.medicine_name=medicinename
medicine.assignedWith="DR.$doc"
        medicine.medicine_time=medicine_time

        if (medicines != null) {
            medicines.add(medicine)

            val users = db.collection("Users").document("${username}:medical info ".trim())
                .update("medicines", medicines)
        }
    }

    private fun get_usermedical_info(username:String?=null): user_midical_info{
        var db = FirebaseFirestore.getInstance()
        var user_medical:user_midical_info= user_midical_info()
        val users = db.collection("Users").document("${username}:medical info ".trim())
            .get().addOnSuccessListener { documentSnapshot ->
             user_medical = documentSnapshot.toObject(user_midical_info::class.java)!!
            }

        return user_medical

    }

}