package com.example.emergency_alert_system.EMP.creation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.emergency_alert_system.MIddle_Layer.CRUD_operations
import com.example.emergencyalertsystem.R
import com.google.firebase.firestore.FirebaseFirestore

class ep_Signup : AppCompatActivity() {
//edittext
  lateinit var epname_text: EditText
    lateinit var   epemail_text: EditText
    lateinit var  epphone_text: EditText
    lateinit var  epnaighb_text: EditText
    lateinit var  epbuildingnum_text: EditText
    lateinit var   eppass_text: EditText
lateinit var  signup:Button
    lateinit var create_ep:CRUD_operations
    var db = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)
     epname_text=findViewById(R.id.EP_name)
     epemail_text=findViewById(R.id.EP_Email)
     epnaighb_text=findViewById(R.id.EP_neghiporhood)
     eppass_text=findViewById(R.id.EP_password)
     epphone_text=findViewById(R.id.EP_phone)
     epbuildingnum_text=findViewById(R.id.building_number)
     signup=findViewById(R.id.ep_signup)

     val numofamblunnce=0
     val numofbed=0
      //  val ep=Emergency_point(EPName,email, phonenum1, naighbourhood, streetname, buildingnum, numofamblunnce, numofbed)
    //create_ep.create_ep(signup,ep)



     signup.setOnClickListener{

             val EPName=epname_text.text.toString().trim()
             val email=epemail_text.text.toString().trim()
             val phonenum1=epphone_text.text.toString().trim()
             val naighbourhood=epnaighb_text.text.toString().trim()
             val pass=eppass_text.text.toString()
             val streetname=""
       //  val ep:Emergency_point
             val buildingnum=epbuildingnum_text.text.toString().trim()
             val ep_map= hashMapOf(
                 "EPName" to EPName,
                 "email" to email,
                 "phone num" to phonenum1,
                 "naighbourhood" to naighbourhood,
                 "buildingnum" to buildingnum,
                 "password" to pass
             )
             val eps=db.collection("Emergency point")
             eps.document(EPName).set(ep_map)
                 .addOnSuccessListener {
                     Toast.makeText(this@ep_Signup,"successfuly added the ep", Toast.LENGTH_SHORT).show()
                 }
                 .addOnFailureListener{
                     Toast.makeText(this@ep_Signup,"failed  added ep",Toast.LENGTH_SHORT).show()
                 }
         }

     }


    }


