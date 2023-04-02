package com.example.emergency_alert_system.user.creation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.emergency_alert_system.user.model.medicine
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_signup.*

class user_signup : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    lateinit var diabetes: CheckBox
    lateinit var heart_d:CheckBox
    lateinit var blood_press:CheckBox
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
    lateinit var userAge:EditText
    lateinit var userphone_text:EditText
    lateinit var userpass_text:EditText
    lateinit var userfirst_relative_text:EditText
    lateinit var usersecond_relative_text:EditText
    lateinit var userthird_relative_text:EditText
    lateinit var userfirst_relative_num:EditText
    lateinit var usersecond_relative_num:EditText
    lateinit var userthird_relative_num:EditText

    //address
    lateinit var naighbourhood:EditText
    lateinit var state:EditText
    lateinit var sigin:TextView
    lateinit var buildingnum_text:EditText
    lateinit var streetname_text:EditText
    lateinit var flatnum_text:EditText


var usermedical:user_midical_info= user_midical_info()
    var  mymedicines : medicine=medicine()
    var  mymedicines2 : medicine=medicine()
    var  mymedicines3 : medicine=medicine()
    var user :user_general_info= user_general_info()
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_signup)

mAuth=FirebaseAuth.getInstance()
        val uid=mAuth.currentUser?.uid
//sign in
        sigin=findViewById(R.id.toSignIn)

        //address
        state=findViewById(R.id.user_state)
        userAge=findViewById(R.id.user_age)
        flatnum_text=findViewById(R.id.flatnum)
        streetname_text=findViewById(R.id.stretname)
        buildingnum_text=findViewById(R.id.Buildnum)
        naighbourhood=findViewById(R.id.naighbourhood)
        diabetes=findViewById(R.id.diabetes)
        heart_d=findViewById(R.id.hd)
        blood_press=findViewById(R.id.bpd)
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

        sigin.setOnClickListener {
            val myIntent = Intent(this,user_Login::class.java)
            startActivity(myIntent)
        }
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
                val age=userAge.text.toString().trim()
                val state=state.text.toString().trim()
                val phone_num=userphone_text.text.toString().trim()
                val password =userpass_text.text.toString().trim()
                val relativies: MutableList<String> = mutableListOf()
                relativies.add(first_relative.text.toString().trim())
                relativies.add(second_relative.text.toString().trim())
                relativies.add(third_relative.text.toString().trim())
                val relativesphonenum: MutableList<String> = mutableListOf()
                relativesphonenum.add(first_relative_phone.text.toString().trim())
                relativesphonenum.add(second_relative_phone.text.toString().trim())
                relativesphonenum.add(third_relative_phone.text.toString().trim())
                val streetname:String=streetname_text.text.toString()
                val naighbourrhood:String=naighbourhood.text.toString().trim()
                val buildingnumb :String=buildingnum_text.text.toString()
                val flatingnumb :String=flatnum_text.text.toString()
                val addr:user_location= user_location()
                addr.username=fullname
                addr.buildingnumb=buildingnumb
                addr.flatingnumb=flatingnumb
                addr.naighbourrhood=naighbourrhood
                addr.streetname=streetname
                addr.state=state

                val medicines: MutableList<medicine>
                medicines=mutableListOf()

                //mymedicines2.username=fullname
                //mymedicines3.username=fullname

                mymedicines.medicine_name =medicine_1.text.toString().trim()
                mymedicines.assignedWith="ME"
                mymedicines.medicine_time="22 PM"
                mymedicines2.medicine_name =medicine_2.text.toString().trim()
                mymedicines2.assignedWith="ME"
                mymedicines2.medicine_time="20 PM"
                mymedicines3.medicine_name =medicine_3.text.toString().trim()
                mymedicines3.assignedWith="ME"
                mymedicines3.medicine_time="21 PM"
                medicines.add(mymedicines)
                medicines.add(mymedicines2)
                medicines.add(mymedicines3)

                val choronic: MutableList<String> =mutableListOf()
                if(diabetes.isChecked){
                    choronic.add("Diabetes")
                }

                if(heart_d.isChecked) {
                    choronic.add("Heart diseases")
                }
                if(blood_press.isChecked) {
                    choronic.add("blood pressure disease")
                }
                usermedical.username=fullname.trim()
                user.username=fullname.trim()
                user.email = email
                user.age=age.toInt()
                user.phone_num = phone_num
                user.password = password
                user.relatives = relativies
                user.relativesphonenum= relativesphonenum
               usermedical.choronic=choronic
                usermedical.medicines= medicines

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        //val myIntent2:Intent = Intent(this@user_signup,user_Login::javaClass)
                        //startActivity(myIntent2)
                        val users=db.collection("USERS")
                        val usersmedicalpath=db.collection("USERS MEDICAL INFO")
                        val useraddresspath=db.collection("USERS Adresses")
                         val uid2=mAuth.uid
                        users.document(uid2!!).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this@user_signup,"successfuly added the user", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(this@user_signup,"failed  added user", Toast.LENGTH_SHORT).show()
                            }

                        usersmedicalpath.document("${user.username}:medical info ".trim()).set(usermedical)
                            .addOnSuccessListener {
                                Toast.makeText(this@user_signup,"successfuly added the user medical_info", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(this@user_signup,"failed  added user medical info", Toast.LENGTH_SHORT).show()
                            }

                        useraddresspath.document("${user.username}:Address ".trim()).set(addr)
                            .addOnSuccessListener {
                                Toast.makeText(this@user_signup,"successfuly added the user address", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(this@user_signup,"failed  added user addres", Toast.LENGTH_SHORT).show()
                            }

                    }
                    else{
                        Toast.makeText(this@user_signup,"erroe auth user"+ (task.exception!!.message.toString()), Toast.LENGTH_SHORT).show()

                    }

                }
// retriveing medicine              }
// waiting list
//    approved
// rejected
// activiate alert2
// acctivate alert1




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
