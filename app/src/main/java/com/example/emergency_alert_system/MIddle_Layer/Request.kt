package com.example.emergency_alert_system.MIddle_Layer

import android.widget.Button
import android.widget.Toast
import com.example.emergency_alert_system.Doctor.Creation.Doctor
import com.example.emergency_alert_system.Doctor.model.Mypatients
import com.example.emergency_alert_system.user.creation.user
import com.example.emergency_alert_system.user.model.medicine
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class Request( var uid:String?=null ,var status:String ?=null,var usern: String?=null , var doocname: String?=null) {

 //var id: String =UUID.randomUUID().toString()

    fun create_request(doc_name: String?,username:String? ){
        var db = FirebaseFirestore.getInstance()
        var req:Request =Request()
        val id: String =UUID.randomUUID().toString()
      req.usern=username
        req.uid=id
       req.status="pending"
        req.doocname=doc_name
        val users=db.collection("User_Request".trim())

        if (username != null) {
            users.document("$username:requests").set(req)
            val doctor=db.collection("Doctor:$doc_name _Request_list".trim())
            doctor.document("request$username").set(req)
        }

      //getting current user
      //getting doc name



    }
    fun approved(username: String?,doc_name: String?){
        var db = FirebaseFirestore.getInstance()
         val User:user=user()
        User.fullname=username
         var Patients: MutableList<String> ?=null
        val patient:Mypatients= Mypatients()
        patient.PATIENTS= arrayListOf()
        patient.PATIENTS!!.add(username!!)
       //update stutus on user side
        db.collection("User_Request").document("$username:requests").update("status" ,"Approved")
//uid doc user

        if (username != null) {
            db.collection("USERS").whereEqualTo("username",username).get().addOnSuccessListener { documents ->
                for (document in documents) {
                   val id= document.id
                    db.collection("USERS").document(id).update("user_docname",doc_name)

                }
            }
            val doctor=db.collection("Doctor:$doc_name _Request_list".trim())
            doctor.document("request$username").delete()
        }


        // add patient to my patient list



        if (username != null) {
            db.collection("Doctor patients").document("$doc_name PATIENTS").set( patient)
        }
        //move patient to list  of patients in doctor view
        }

        fun rejected(username:String?,doc_name: String?){
            var db = FirebaseFirestore.getInstance()
            //remove from  doc waiting list view &db
           //update in db of user
            db.collection("User_Request".trim()).document("$username:request").update(
                "status" ,"Rejected")

            val doctor=db.collection("Doctor:$$doc_name _Request_list".trim()).document("$username:requests").delete()
        }



}

