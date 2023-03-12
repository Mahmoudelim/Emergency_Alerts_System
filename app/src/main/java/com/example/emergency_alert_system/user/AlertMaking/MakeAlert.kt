package com.example.emergency_alert_system.user.AlertMaking

import com.example.emergency_alert_system.EMP.creation.Emergency_point
import com.example.emergency_alert_system.user.creation.user_location
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class MakeAlert {
    //login user name
   var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
   var firestore:FirebaseFirestore=FirebaseFirestore.getInstance()
    val UID =mAuth.currentUser!!.uid
    var negihborhoodEP: MutableList<Emergency_point>? = null
    val userref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->
        val nm: String? = document.data!!["username".trim()].toString()

        //get user location from fireStore
        var userLocation:user_location

        val docRef: DocumentReference = firestore.collection("USERS Adresses").
        document("$nm:Address")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot != null) {
                     userLocation = documentSnapshot.toObject(user_location::class.java)!!
                }
            }
        }
        fun nearestGPSCoridnate(userLocation: user_location,neighborhood:String):String{
            //find nearest in neighborhood
            //array take all EP in this neighborhood
            var nearestEP :Emergency_point?=null
            val neighborhoodRef: CollectionReference = firestore.collection("Emergency point")
            neighborhoodRef.whereArrayContains("naighbourhood", "Badr City")
          neighborhoodRef.get().addOnCompleteListener{ task ->
              if (task.isSuccessful) {
                  val documentSnapshot = task.result
                  if (documentSnapshot != null) {
                      negihborhoodEP = documentSnapshot.toObjects(Emergency_point::class.java)!!
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
                if (lat!! <smallestLat && long!!<smallestLong)
                {
                    nearestEP =EP
                }
            }
        return nearestEP!!.EPName!!
        }


    fun findNearestEP( userLocation: user_location ):String{
        var nearestEp:String=""
        if (userLocation.state=="Cairo")
        {
            when(userLocation.naighbourrhood.toString()) {
                "Elmarg" -> userLocation.naighbourrhood?.let {
                    nearestGPSCoridnate(userLocation, it)
                }
                "Badr City" -> userLocation.naighbourrhood?.let {
                    nearestGPSCoridnate(userLocation, it)
                }
            }
        }
        else if (userLocation.state=="Alex")
        {

        }
        return nearestEp
    }
    fun AlertToEp(){}
    fun AlertToRelatives(){}
    fun AlertToDoc(){}
}}