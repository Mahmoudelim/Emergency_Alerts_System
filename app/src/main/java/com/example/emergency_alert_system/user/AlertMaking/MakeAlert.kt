package com.example.emergency_alert_system.user.AlertMaking

import android.util.Log
import android.widget.Toast
import com.example.emergency_alert_system.EMP.creation.Emergency_point
import com.example.emergency_alert_system.user.creation.user_location
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
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
                    Log.i("userLoc", userLocation.toString())
                }
            }
        }
    }
    val result=ArrayList<Emergency_point>()

        fun nearestGPSCoridnate(userLocation: user_location,neighborhood:String):String{

            //find nearest in neighborhood
            //array take all EP in this neighborhood
            var nearestEP =Emergency_point()
            Log.i("neeee",neighborhood.toString())
            val neighborhoodRef= firestore.collection("Emergency point")
                .whereEqualTo("naighbourhood".trim(), neighborhood)
                .addSnapshotListener{ snapshot,exception ->
                    if(exception !=null)
                    {
                        Log.e("snapshotException",exception.toString())
                        return@addSnapshotListener
                    }
                    if (snapshot!=null){
                        for (document in snapshot.documents){
                            var doc=document.toObject(Emergency_point::class.java)
                            result.add(doc!!)
                            Log.i("neArray",result.toString())

                        }
                    }




                     Log.i("nearest", result.toString())


              }

            Log.i("nearest", result.toString())

            // iterate in this list
            for(EP in result!!)
            {

                var lat = userLocation.latitude
                var long= userLocation.longitude
                var targetlat=EP.latitude
                var targetLong=EP.longitude
                if (lat!!.minus(targetlat!!) < nearestEP.latitude!!.minus(targetlat!!)
                    &&long!!.minus(targetLong!!) < nearestEP.longitude!!.minus(targetLong))
                {
                    nearestEP =EP

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
             // nearestEp="Badr"
          }
            else if (userLocation.naighbourrhood=="Elmarg")
          {
              nearestEp=nearestGPSCoridnate(userLocation,"Elmarg")
             //  nearestEp="Elmarg"
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