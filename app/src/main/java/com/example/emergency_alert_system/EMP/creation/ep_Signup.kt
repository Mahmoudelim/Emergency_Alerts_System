package com.example.emergency_alert_system.EMP.creation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emergencyalertsystem.R
import com.google.firebase.firestore.FirebaseFirestore

class ep_Signup : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
   // lateinit var EPName: String
   // lateinit var email: String
   // lateinit var phonenum1:Number
   // lateinit var naighbourhood:String
    //lateinit var streetname: String
    //var buildingnum:Int=0
    //lateinit  var mangername: String
    // var numofamblunnce:Int=0
    // var numofbed:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)
    //    val ep=Emergency_point(EPName,email, phonenum1, naighbourhood, streetname, buildingnum, mangername, numofamblunnce, numofbed)
    }

}
/*
  db.collection("Emergency_point")
  .add(ep)
  .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
      @Override
      public void onSuccess(DocumentReference documentReference) {
          Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
      }
  })
  .addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
          Log.w(TAG, "Error adding document", e);
      }
  });

   */