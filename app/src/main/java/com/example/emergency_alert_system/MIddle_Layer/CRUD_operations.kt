package com.example.emergency_alert_system.MIddle_Layer

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.emergency_alert_system.Doctor.Creation.Doctor
import com.example.emergency_alert_system.EMP.creation.Emergency_point
import com.example.emergency_alert_system.user.creation.*
import com.google.firebase.firestore.FirebaseFirestore

class CRUD_operations {
    var db = FirebaseFirestore.getInstance()
    fun create_user( btn:Button,
                     user1:user_general_info,
                     user2:user_midical_info,
                     user3:user_location,
                     user4:user_vitaldata
    ){


    }
    fun create_doctor( btn:Button,
                       doctor: Doctor
                     ){

        btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                val doctors= db.collection("Doctor")
                //doctors.document(doctor.doctorname).set(doctor)

            }

        })

    }

    fun create_ep(btn: Button,ep:Emergency_point ){
        btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
        val eps=db.collection("Emergency point")
            }

        })
    }

    //when user request
    fun add_to_doc_waitinglist(  patientname:String ,doctorname: String ){
        lateinit var waiting_patient: MutableList<String>
        waiting_patient.add(patientname)
        val doctors= db.collection("Doctor")
        doctors.document("$doctorname  waiting list").set(waiting_patient)
    }
    //request approved
    fun add_to_doc_patiente_list(patientname:String ,doctorname: String ){
        lateinit var  patientslist: MutableList<String>
        patientslist.add(patientname)
        val doctors= db.collection("Doctor")
        doctors.document("$doctorname  patientes list" ).set(patientslist)
    }


    //alert one prerequests
    // 1_ medical info of user

fun Alert1_user_medicalinfo(username:String) {
    val docRef = db.collection("User").document("$username:medical info \"")
    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }

    }
// 2_ address of user
   fun  Alert1_user_address(username:String) {
       val docRef = db.collection("User").document("$username:Address  ")
       docRef.get()
           .addOnSuccessListener { document ->
               if (document != null) {
                   Log.d(TAG, "DocumentSnapshot data: ${document.data}")
               } else {
                   Log.d(TAG, "No such document")
               }
           }
           .addOnFailureListener { exception ->
               Log.d(TAG, "get failed with ", exception)
           }

   }

    fun  Alert1_user_generalinfo(username:String) {
        val docRef = db.collection("User").document(username)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }


    fun  Alert1_user_report(username:String) {
        val docRef = db.collection("User").document("$username:last_repot")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }



}
