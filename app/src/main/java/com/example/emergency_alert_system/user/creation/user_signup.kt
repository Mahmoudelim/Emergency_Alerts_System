package com.example.emergency_alert_system.user.creation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.emergencyalertsystem.R
import com.google.firebase.firestore.FirebaseFirestore

class user_signup : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
   lateinit var fullname: String
    lateinit var email: String
    lateinit var phone_num: String
    lateinit var password : String
    lateinit var user_docname:String
   lateinit var relativies: MutableList<String>
    lateinit  var relativesphonenum: MutableList<String>
    lateinit var streetname:String
    lateinit var naighbourrhood:String
    var buildingnumb :Int=0
    var floornumb :Int=0
    var flatingnumb :Int=0
    lateinit var medicines: MutableList<String>
    lateinit var choronic: MutableList<String>

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_signup)
        //  val userinfoo=user_general_info(fullname,email,phone_num,password,user_docname,relativies,relativesphonenum)
        // val  usermedicaldata= user_midical_info(fullname,medicines,choronic)
        //val user_location=user_location(fullname,streetname,naighbourrhood,buildingnumb, floornumb, flatingnumb)

        val btn: ImageButton = findViewById(R.id.add_btn1)
        val  btn2: ImageButton = findViewById(R.id.add_btn2)
        val btn3: ImageButton = findViewById(R.id.add_btn3)
        val editTxt1:EditText=findViewById(R.id.mid1)
        val editTxt2:EditText=findViewById(R.id.mid2)
        val editTxt3:EditText=findViewById(R.id.mid3)
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                 editTxt2.visibility=View.VISIBLE
                 btn2.visibility=View.VISIBLE

            }

        })
        btn2.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                editTxt3.visibility=View.VISIBLE
                btn3.visibility=View.VISIBLE
            }

        })
    }
}
/*
  fun write_general_info(){
   db.collection("user")
   .add(userinfoo)
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
}
    */
/* fun write_medical_data(){
   db.collection("user")
   .add(usermedicaldata)
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
}
 /*
    fun write_useraddres(){
 */
    db.collection("user")
    .add(user_location)
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
}
     */
/*
   fun reading_and_write_VITALDATA()
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
