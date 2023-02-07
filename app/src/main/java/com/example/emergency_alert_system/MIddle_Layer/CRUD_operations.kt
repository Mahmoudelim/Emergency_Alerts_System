package com.example.emergency_alert_system.MIddle_Layer

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
        val user= db.collection("User")
        user.document(user1.fullname).set(user1)
        user.document("  ${user1.fullname}:medical info ").set(user2)
        user.document("  ${user1.fullname}:Address ").set(user3)
        user.document("  ${user1.fullname}:vital Data ").set(user4)

    }
    fun create_doctor( btn:Button,
                       doctor: Doctor
                     ){

        val doctors= db.collection("Doctor")
        doctors.document(doctor.doctorname).set(doctor)
    }
    //when user request
    fun add_to_doc_waitinglist(  patientname:String ,doctorname: String ){
        lateinit var waiting_patient: MutableList<String>
        waiting_patient.add(patientname)
        val doctors= db.collection("Doctor")
        doctors.document("$doctorname  waiting list").set(waiting_patient)
    }
    fun add_to_doc_patiente_list(patientname:String ,doctorname: String ){
        lateinit var  patientslist: MutableList<String>
        patientslist.add(patientname)
        val doctors= db.collection("Doctor")
        doctors.document("$doctorname  patientes list" ).set(patientslist)
    }
    fun create_ep(btn: Button,ep:Emergency_point ){

        val eps=db.collection("Emergency point")
    }





}