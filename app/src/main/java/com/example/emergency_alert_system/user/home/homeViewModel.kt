package com.example.emergency_alert_system.user.home

import androidx.lifecycle.ViewModel
//import com.example.emergency_alert_system.user.creation.user_vitaldata
import com.google.firebase.firestore.FirebaseFirestore

class homeViewModel : ViewModel() {
    var db = FirebaseFirestore.getInstance()
    lateinit var fullname: String
    lateinit var pulserate:String
    var tempertuture:Int=0
    var pressure: Int=0
    var cuurent_user:String=""
    //val user_vital_data= user_vitaldata(fullname,pulserate,tempertuture,pressure)
}
/*
fun reading_and_write_VITALDATA(username)
//from package vitaldataclass reading
db.collection("user")
.add(user_vitaldata)
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