package com.example.emergency_alert_system.user.AlertMaking

import android.util.Log
import android.widget.Toast
import com.example.emergency_alert_system.EMP.creation.Emergency_point
import com.example.emergency_alert_system.user.creation.user_location
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

var nm :String = ""
var Nearest_EP:String=""
var streetName=""
var age:Number=2
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
                    nearestEP=result[0]
                    for (EP in result) {
                        val lat = userLocation.latitude
                        val long = userLocation.longitude
                        val targetlat = EP.latitude
                        val targetLong = EP.longitude
                        val nearestEPLat=nearestEP!!.latitude
                        val nearestEPLong=nearestEP.longitude
                        if (lat!!.minus(targetlat!!) < nearestEPLat!!.minus(targetlat)!!
                            && long!!.minus(targetLong!!) < nearestEPLong!!.minus(targetLong)!!)
                        {
                            nearestEP = EP
                        }
                    }


                    // move the return statement here
                    nearestEPName = nearestEP.EPName.toString()
                    Log.i("nearestEPName", nearestEPName.toString())
                    Nearest_EP=nearestEP.EPName.toString()
                    return@addOnCompleteListener
                }
            }

            Log.i("nearest", nearestEP.toString())
        }
        Log.i("nearest2333", Nearest_EP)
        return Nearest_EP
    }
    fun findNearestEP(userLocation: user_location): String? {
        var nearestEp: String? = null
        if (userLocation.state == "Cairo".trim()) {
            Log.i("iiiii", userLocation.state.toString())
            when (userLocation.naighbourrhood) {
                "Elmarg" -> nearestEp = nearestGPSCoridnate(userLocation, "Elmarg")
                "Badr City"->nearestEp=nearestGPSCoridnate(userLocation,"Badr City")
                "ainshams" -> nearestEp = nearestGPSCoridnate(userLocation, "ainshams")
            }
        } else if (userLocation.state == "Alex") {

        }
        return nearestEp
    }

    fun AlertToEp() {

        firestore.collection("USERS Adresses").document("${nm}:Address ".trim()).get().addOnSuccessListener {document->
            streetName=document.getString("streetname").toString()
            Log.i("streeetName",streetName)

        }
        firestore.collection("USERS").document(UID).get().addOnSuccessListener {document->
            age = document.getLong("age") ?: 0 // Use getLong() to retrieve the age as a number
            Log.i("agee", age.toString())

        }

        Log.i("streetname",streetName)

        val alertMap = hashMapOf(
            "user_name" to nm,
            "user_age" to age ,
            "street_name" to streetName



        )
        val nearestEp: String? = userLocation?.let { findNearestEP(it) }
        firestore.collection("$nearestEp requests").document("$nm request").set(alertMap)

    }

    fun AlertToRelatives() {}
    fun AlertToDoc() {}
}