package com.example.emergency_alert_system.user.creation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_signup.*

class user_signup : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    private lateinit var mAuth:FirebaseAuth
    lateinit var btn: ImageButton
    lateinit var  btn2: ImageButton
    lateinit var btn3: ImageButton
    lateinit var signup: Button
    lateinit var medicine_1:EditText
    lateinit var medicine_2:EditText
    lateinit var medicine_3:EditText
    lateinit var username_text:EditText
    lateinit var useremail_text:EditText
    lateinit var userphone_text:EditText
    lateinit var userpass_text:EditText
    lateinit var userfirst_relative_text:EditText
    lateinit var usersecond_relative_text:EditText
    lateinit var userthird_relative_text:EditText
    lateinit var userfirst_relative_num:EditText
    lateinit var usersecond_relative_num:EditText
    lateinit var userthird_relative_num:EditText

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_signup)
        //  val userinfoo=user_general_info(fullname,email,phone_num,password,user_docname,relativies,relativesphonenum)
        // val  usermedicaldata= user_midical_info(fullname,medicines,choronic)

        //val user_location=user_location(fullname,streetname,naighbourrhood,buildingnumb, floornumb, flatingnumb)
mAuth=FirebaseAuth.getInstance()
        signup=findViewById(R.id.signup_user)
     username_text=findViewById(R.id.user_name)
        useremail_text=findViewById(R.id.user_Email)
        userphone_text=findViewById(R.id.user_phone)
         btn = findViewById(R.id.add_btn1)
        btn2 = findViewById(R.id.add_btn2)
         btn3 = findViewById(R.id.add_btn3)
         medicine_1=findViewById(R.id.mid1)
         medicine_2=findViewById(R.id.mid2)
        medicine_3=findViewById(R.id.mid3)
        userpass_text=findViewById(R.id.user_password)
        userfirst_relative_text=findViewById(R.id.first_relative)
        userfirst_relative_num=findViewById(R.id.first_relative_phone)
        usersecond_relative_text=findViewById(R.id.second_relative)
        usersecond_relative_num=findViewById(R.id.second_relative_phone)
        userthird_relative_text=findViewById(R.id.third_relative)
        userthird_relative_num=findViewById(R.id.third_relative_phone)

        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                medicine_2.visibility=View.VISIBLE
                 btn2.visibility=View.VISIBLE

            }

        })
        btn2.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                medicine_3.visibility=View.VISIBLE
                btn3.visibility=View.VISIBLE
            }

        })

        signup.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val fullname=username_text.text.toString().trim()
                val email=userpass_text.text.toString().trim()

                val phone_num=userphone_text.text.toString().trim()
                val password =userpass_text.text.toString().trim()

                //val user_docname:String
                val relativies: MutableList<String> = mutableListOf("")
                relativies.add(first_relative.text.toString().trim())
                relativies.add(second_relative.text.toString().trim())
                relativies.add(third_relative.text.toString().trim())
                val relativesphonenum: MutableList<String> = mutableListOf("")
                relativesphonenum.add(first_relative_phone.text.toString().trim())
                relativesphonenum.add(second_relative_phone.text.toString().trim())
                relativesphonenum.add(third_relative_phone.text.toString().trim())
               // val streetname:String
               // val naighbourrhood:String
               // val buildingnumb :Int=0
               // val floornumb :Int=0
               // val flatingnumb :Int=0
                val medicines: MutableList<String>
                medicines=mutableListOf("")
                medicines.add(medicine_1.text.toString().trim())
                medicines.add(medicine_2.text.toString().trim())
                medicines.add(medicine_3.text.toString().trim())

                val choronic: MutableList<String>

                val user_map= hashMapOf(
                    "Name" to fullname,
                    "email" to email,
                    "phonenum" to phone_num,
                    "password" to password)
                val user_contact_map= hashMapOf(
                "relatives" to relativies,
                  "relatives_phone" to relativesphonenum)
                val user_medical_info= hashMapOf(
                    //to do choronic
                  "medicines" to medicines
                )
                //user address
                //user vital data
                //user evaluation

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){


                        val users=db.collection("USERS")
                        users.document(fullname).set(user_map)
                            .addOnSuccessListener {
                                Toast.makeText(this@user_signup,"successfuly added the user", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(this@user_signup,"failed  added user", Toast.LENGTH_SHORT).show()
                            }
                        users.document("  ${fullname}:contact info ").set(user_contact_map)
                            .addOnSuccessListener {
                                Toast.makeText(this@user_signup,"successfuly added the user contacts", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(this@user_signup,"failed  added user contacts", Toast.LENGTH_SHORT).show()
                            }
                        users.document("  ${fullname}:medical info ").set(user_medical_info)
                            .addOnSuccessListener {
                                Toast.makeText(this@user_signup,"successfuly added the user medical_info", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(this@user_signup,"failed  added user medical info", Toast.LENGTH_SHORT).show()
                            }

                    }
                    else{
                        Toast.makeText(this@user_signup,"erroe auth user"+ (task.exception!!.message.toString()), Toast.LENGTH_SHORT).show()

                    }
                }





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
