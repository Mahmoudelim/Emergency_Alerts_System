package com.example.emergency_alert_system.user.AlertMaking

import android.util.Log
import android.widget.Toast
import com.example.emergency_alert_system.EMP.creation.Emergency_point
import com.example.emergency_alert_system.user.creation.user_location
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

var nm :String = ""
var Nearest_EP:String=""
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
                    Log.i("userLoc", userLocation.toString())
                }
            }
        }
    }
    var result= mutableListOf<Emergency_point>()
    fun nearestGPSCoridnate(userLocation: user_location, neighborhood:String) :String{
       var nearestEP = Emergency_point()
       var nearestEPName=""
       val neighborhoodRef = firestore.collection("Emergency point").whereEqualTo("naighbourhood", neighborhood)
       neighborhoodRef.get().addOnCompleteListener { task ->
           if (task.isSuccessful) {
               val documentSnapshot = task.result
               if (documentSnapshot != null) {
                    result = documentSnapshot.toObjects(Emergency_point::class.java)!!
                   Log.i("nearestEPArray", result.toString())
                   nearestEP=result[0]
                   Log.i("nearestEP", nearestEP.toString())
                   for (EP in result) {
                       val lat = userLocation.latitude
                       Log.i("lat in usesrLoc",lat.toString())
                       Log.i("nearestEP1", nearestEP.toString())
                       val long = userLocation.longitude
                       Log.i("long in usesrLoc",long.toString())
                       Log.i("nearestEP2", nearestEP.toString())
                       val targetlat = EP.latitude
                       Log.i("target lat in usesrLoc",targetlat.toString())
                       Log.i("nearestEP3", nearestEP.toString())
                       val targetLong = EP.longitude
                       Log.i("target long in usesrLoc",targetLong.toString())
                       Log.i("nearestEP4", nearestEP.toString())
                       val nearestEPLat=nearestEP!!.latitude
                       Log.i("nearestEPlat", nearestEPLat.toString())
                       val nearestEPLong=nearestEP.longitude
                       Log.i("nearestEPlong", nearestEPLong.toString())
                       if (lat!!.minus(targetlat!!) < nearestEPLat!!.minus(targetlat)!!
                           && long!!.minus(targetLong!!) < nearestEPLong!!.minus(targetLong)!!)
                       {
                           Log.i("nearestEP5", nearestEP.toString())
                           nearestEP = EP
                           Log.i("nearestEP6", nearestEP.toString())
                       }
                   }


                   // move the return statement here
                    nearestEPName = nearestEP.EPName.toString()
                   Log.i("nearestEPName", nearestEPName.toString())
                    Nearest_EP=nearestEP.EPName.toString()
                   return@addOnCompleteListener
               }
           }
           //

           // iterate in this list


                    Log.i("nearest", nearestEP.toString())
                }



           // return an empty string for now



      return Nearest_EP
   }

       fun findNearestEP(userLocation: user_location): String? {
           var nearestEp: String? = null
           if (userLocation.state == "Cairo".trim()) {

               Log.i("iiiii", userLocation.state.toString())
               when (userLocation.naighbourrhood) {
                   "Elmarg" -> nearestEp = nearestGPSCoridnate(userLocation, "Elmarg")
                    "Badr City"->nearestEp=nearestGPSCoridnate(userLocation,"Badr City")
               }
           } else if (userLocation.state == "Alex") {

           }
           return nearestEp
       }

       fun AlertToEp() {
           val alertMap = hashMapOf(
               "user_name" to nm,
                "user_age" to "44" ,
               "street_name" to "Ahmed Essmat"



           )
           val nearestEp: String? = userLocation?.let { findNearestEP(it) }
           firestore.collection("$nearestEp requests").document().set(alertMap)

       }

       fun AlertToRelatives() {}
       fun AlertToDoc() {}
   }