package com.example.emergency_alert_system.user.AlertMaking

import android.util.Log
import android.widget.Toast
import com.example.emergency_alert_system.EMP.creation.Emergency_point
import com.example.emergency_alert_system.user.creation.user_location
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

var nm :String = ""

class MakeAlert {
    //login user name
   var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
   var firestore:FirebaseFirestore=FirebaseFirestore.getInstance()
    val UID =mAuth.currentUser!!.uid
    var negihborhoodEP: MutableList<Emergency_point>? =mutableListOf()
    var userLocation: user_location? = null
    val userref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->
         nm = document.data!!["username".trim()].toString()

        //get user location from fireStore


        val docRef: DocumentReference =
            firestore.collection("USERS Adresses").document("$nm:Address")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot != null) {
                    userLocation = documentSnapshot.toObject(user_location::class.java)!!
                }
            }
        }
    }
        fun nearestGPSCoridnate(userLocation: user_location,neighborhood:String):String{
            //find nearest in neighborhood
            //array take all EP in this neighborhood
            var nearestEP =Emergency_point()
            val neighborhoodRef: CollectionReference = firestore.collection("Emergency point".trim())
            neighborhoodRef.whereArrayContains("naighbourhood".trim(), neighborhood)
          neighborhoodRef.get().addOnCompleteListener{ task ->
              if (task.isSuccessful) {
                  val documentSnapshot = task.result
                  Log.i("fef", "sucssed")
                  if (documentSnapshot != null) {
                      negihborhoodEP = documentSnapshot.toObjects(Emergency_point::class.java)!!
                     Log.i("nearest", negihborhoodEP.toString())

                  }
              }
          }
            // iterate in this list
            for(EP in negihborhoodEP!!)
            {

                 var smallestLat :Double = 0.0
                 var smallestLong:Double =0.0
                var lat = userLocation.latitude?.minus(EP.latitude!!)
                var long= userLocation.longitude?.minus(EP.longitude!!)
                if (lat!! <=smallestLat && long!!<=smallestLong)
                {
                    nearestEP =EP
                    smallestLat=lat
                    smallestLong=long
                }
            }
        return nearestEP!!.EPName!!
        }


    fun findNearestEP( userLocation: user_location ): String? {
           var nearestEp: String? =null
        if (userLocation.state=="Cairo".trim())
        {
          if(userLocation.naighbourrhood=="Badr City".trim())
          {
              nearestEp=nearestGPSCoridnate(userLocation,"Badr City")
              nearestEp="Badr"
          }
            else if (userLocation.naighbourrhood=="Elmarg")
          {
              nearestEp=nearestGPSCoridnate(userLocation,"Elmarg")
             // nearestEp="Elmarg"
            }

        }
        else if (userLocation.state=="Alex")
        {

        }
    return nearestEp
    }
    fun AlertToEp(){
        val alertMap= hashMapOf(
            "name" to nm


        )
        val nearestEp: String? = userLocation?.let { findNearestEP(it) }
        firestore.collection("$nearestEp requests").document().set(alertMap)

    }
    fun AlertToRelatives(){}
    fun AlertToDoc(){}
}