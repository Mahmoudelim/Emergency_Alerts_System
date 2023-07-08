package com.example.emergency_alert_system.user.creation

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.emergency_alert_system.EMP.creation.Emergency_point
import com.example.emergency_alert_system.track_location.LocationService
import com.example.emergency_alert_system.user.UserDachboard
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.Math.*
import java.util.*
import kotlin.math.pow

class user_Login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var useremail_text: EditText
     var nm:String="recent uSer"

    lateinit var userpassword_text:EditText
    lateinit var login_user: Button
    lateinit var mAuth2: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    var db = FirebaseFirestore.getInstance()
    lateinit var login_user2: Button
    lateinit var name:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        mAuth=FirebaseAuth.getInstance()
        mAuth2= FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
       /* val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val rememberMeCheckBox = findViewById<CheckBox>(R.id.rememberMe)
        val emailEditText = findViewById<EditText>(R.id.user_editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.user_editTextTextPassword)

// Load saved email and password from shared preferences
        val savedEmail = sharedPref.getString("email", "")
        val savedPassword = sharedPref.getString("password", "")

        emailEditText.setText(savedEmail)
        passwordEditText.setText(savedPassword)

        rememberMeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("rememberMe", isChecked).apply()
        }
*/
        login_user=findViewById(R.id.user_log)

       useremail_text=findViewById(R.id.user_editTextTextEmailAddress)
        userpassword_text=findViewById( R.id.user_editTextTextPassword)


        login_user.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val email=useremail_text.text.toString().trim()
                val password=userpassword_text.text.toString().trim()
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){

                       /* val rememberMe = sharedPref.getBoolean("rememberMe", false)
                        if (rememberMe) {
                            sharedPref.edit().putString("email", emailEditText.text.toString()).apply()
                            sharedPref.edit().putString("password", passwordEditText.text.toString()).apply()
                        }*/
                        mAuth2= FirebaseAuth.getInstance()
                        firestore=FirebaseFirestore.getInstance()
                        val UID =mAuth.currentUser!!.uid
                        val userref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->

                            val nm: String? = document.data!!["username".trim()].toString()

                            val db = FirebaseFirestore.getInstance()
                            val docref = db.collection("/USERS MEDICAL INFO").document("/$nm:medical info")
                            docref.get().addOnSuccessListener { documentSnapshot ->


                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                    docref.get().addOnSuccessListener { documentSnapshot ->
                                        val userInfo = documentSnapshot.toObject(user_midical_info::class.java)
                                        if (userInfo != null) {
                                            userInfo.medicines!!.forEach {
                                                val medicineTime = it.medicine_time
                                                val medicineTimeList = mutableListOf<Map<String, Any>>()
                                                if (medicineTime != null) {
                                                    medicineTimeList!!.add(medicineTime)
                                                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener { task ->
                                                        if (task.isSuccessful) {
                                                            val token = task.result
                                                            if (token != null) {
                                                                // Store the token, medicine name, and time in Firestore
                                                                val medicineAlarm = hashMapOf(
                                                                    "medicine_name" to it.medicine_name,
                                                                    "medicine_time" to it.medicine_time,
                                                                    "fcm_token" to token
                                                                )
                                                                firestore.collection("medicine_alarms").document().set(medicineAlarm)
                                                                    .addOnSuccessListener {
                                                                        Log.d(TAG, "Medicine alarm added to Firestore")
                                                                    }
                                                                    .addOnFailureListener { e ->
                                                                        Log.e(TAG, "Error adding medicine alarm to Firestore", e)
                                                                    }
                                                            }
                                                        } else {
                                                            Log.e(TAG, "Error getting FCM token", task.exception)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }.addOnFailureListener { e ->
                                        Log.e(ContentValues.TAG, "Error retrieving medical info for user $nm from Firestore", e)
                                    }
                                } else {
                                    Log.e(ContentValues.TAG, "User $nm does not have medical info in Firestore")
                                }


                            }
                        }


                       updateUser()
                       // addNeghborhood()
                  //      Toast.makeText(this@user_Login," ${currentuser.toString()} ",Toast.LENGTH_SHORT).show()
                       val intent=Intent(this@user_Login,UserDachboard().javaClass)
                       startActivity(intent)

                        Intent(applicationContext, LocationService::class.java).apply {
                            action= LocationService.ActionStart
                            startService(this)
                        }
                        finish()
                    }
                    else{
                        Toast.makeText(this@user_Login,"Error login user"+ (task.exception!!.message.toString()), Toast.LENGTH_SHORT).show()
                    }
                    }
                }}




        )}
    data class Neighborhood(val name: String, val latitude: Double, val longitude: Double)

 /*
    fun addNeghborhood(){

            // List of neighborhoods
            val neighborhoods = listOf(
                Neighborhood("Haddayek Al-Qubbah", 30.0821548, 31.2825119),
                Neighborhood("Abdeen", 30.044988, 31.2488627),
                Neighborhood("Zamalek", 30.0559235, 31.22193191198287),
                Neighborhood("Maadi", 29.9796486, 31.2547696),
                Neighborhood("Dokki", 30.0263679, 31.2051094),
                Neighborhood("Heliopolis", 30.1005985, 31.3329136),
                Neighborhood("Mohandessin", 30.0546414, 31.29099512433335),
                Neighborhood("Fifth Settlement", 30.003804549999998, 31.424935726802204),
                Neighborhood("Giza", 30.01580945, 31.332779718798953),
                Neighborhood("Zeitoun", 30.0998365, 31.3082194),
                Neighborhood("El Marg", 30.1442089, 31.3601851),
                Neighborhood("Helwan", 29.8500001, 31.333333),
                Neighborhood("Matariya", 30.1213801, 31.3139749),
                Neighborhood("Badr City", 30.142806, 31.742824),
                Neighborhood("Shorouk City", 30.1488541, 31.6295623),
                Neighborhood("Raml Station", 30.027578, 31.2526837),
                Neighborhood("Mansheya", 29.9091281, 31.2912838),
                Neighborhood("Helmeya", 30.1125623, 31.3342801),
                Neighborhood("Garden City", 30.03472205, 31.231187914963712),
                Neighborhood("Kasr El Nil", 30.0468469, 31.2362954),
                Neighborhood("Nasr City", 30.0521177, 31.3422045),
                Neighborhood("Waily", 30.0668177, 31.2760789),
                Neighborhood("Abbaseya", 30.066667, 31.283333),
                Neighborhood("Basateen", 30.00028485, 31.26080713897211),
                Neighborhood("Ain Shams", 30.1304761, 31.3167491)
            )

            // Print each neighborhood
            for (neighborhood in neighborhoods) {
              firestore.collection("Neighborhood").document(neighborhood.name).set(neighborhood)
            }
        }
*/
    fun updateUser(){
        var mAuth:FirebaseAuth
        var firestore:FirebaseFirestore
        mAuth= FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        val UID =mAuth.currentUser!!.uid
        val userref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->
            val nm: String? = document.data!!["username".trim()].toString()
            firestore.collection("USERS Adresses").document("$nm:Address").get().addOnSuccessListener { document ->
                val userlat :Double? = document.data!!["latitude".trim()] as Double?
                val userLong: Double? = document.data!!["longitude".trim()] as Double?
                if (userlat != null) {
                    if (userLong != null) {
                        findNearestNeighborhood(userlat,userLong,onSuccess = { nearestNeighborhood ->
                            if (nearestNeighborhood != null) {
                                println("Nearest neighborhood: ${nearestNeighborhood.name}")
                                firestore.collection("USERS Adresses").document("$nm:Address").update("naighbourrhood","${nearestNeighborhood.name}")
                            } else {
                                println("No neighborhoods found.")
                            }
                        },
                            onFailure = { exception ->
                                println("Error finding neighborhoods: $exception")


                            }
                        )
                    }
                }



        }
    }
}


    fun findNearestNeighborhood(userLat: Double, userLng: Double, onSuccess: (Neighborhood?) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Neighborhood").get()
            .addOnSuccessListener { result ->
                var nearestNeighborhood: Neighborhood? = null
                var shortestDistance = Double.MAX_VALUE
                for (document in result) {
                    val name = document.getString("name")
                    val latitude = document.getDouble("latitude")
                    val longitude = document.getDouble("longitude")
                    if (name != null && latitude != null && longitude != null) {
                        val dLat = Math.toRadians(latitude - userLat)
                        val dLng = Math.toRadians(longitude - userLng)
                        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(userLat)) * cos(Math.toRadians(latitude)) * sin(dLng / 2).pow(2)
                        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
                        val distance = 6371 * c // Earth's radius is 6,371 km
                        if (distance < shortestDistance) {
                            shortestDistance = distance
                            nearestNeighborhood = Neighborhood(name, latitude, longitude)
                        }
                    }
                }
                onSuccess(nearestNeighborhood)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
    /*fun addHospitals() {


        val hospitals = listOf(
            Emergency_point(
                "Haddayek Al-Qobba Hospital",
                "h@gmail.com",
                null,
                "Haddayek Al-Qobba",
                null,
                null,
                "123456",
                31.2760189,
                30.0736825
            ),
            Emergency_point(
                "Abdeen Hospital",
                "ab@gmail.com",
                null,
                "Abdeen",
                null,
                null,
                "123456",
                31.23766980516448,
                30.054531400000002
            ),
            Emergency_point(
                "Zamalek Hospital",
                "z@gmail.com",
                null,
                "Zamalek",
                null,
                null,
                "123456",
                31.22474054417667,
                30.0446629
            ),
            Emergency_point(
                "Maadi Hospital",
                "M@gmail.com",
                null,
                "Maadi",
                null,
                null,
                "123456",
                31.2429973,
                29.9700229
            ),
            Emergency_point(
                "New Cairo Hospital",
                "NC@gmail.com",
                null,
                "Fifth Settlement",
                null,
                null,
                "123456",
                31.3368939,
                30.1058743
            ),
            Emergency_point(
                "Mohandessin Hospital",
                "moh@gmail.com",
                null,
                "Mohandessin",
                null,
                null,
                "123456",
                31.200597619876515,
                30.05336655
            ),
            Emergency_point(
                "Fifth Settlement Hospital",
                "f@gmail.com",
                null,
                "Fifth Settlement",
                null,
                null,
                "123456",
                31.4181409,
                30.0147462
            ),
            Emergency_point(
                "Giza Hospital",
                "G@gmail.com",
                null,
                "Giza",
                null,
                null,
                "123456",
                31.200597619876515,
                30.05336655
            ),
            Emergency_point(
                "Zeitoun Hospital",
                "Z@gmail.com",
                null,
                "Zeitoun",
                null,
                null,
                "123456",
                31.315370953740157,
                30.1043151
            ),
            Emergency_point(
                "Helwan Hospital",
                "h@gmail.com",
                null,
                "Helwan",
                null,
                null,
                null,
                31.3080686,
                29.7837659
            ),
            Emergency_point(
                "Badr2 Hospital",
                "bad@gmail.com",
                null,
                "Badr City",
                null,
                null,
                "123456",
                31.7233948,
                30.1451471
            ),
            Emergency_point(
                "Shorouk City Hospital",
                "sh@gmail.com",
                null,
                "Shorouk City",
                null,
                null,
                "123456",
                31.7233948,
                30.1451471
            ),
            Emergency_point(
                "Mansheya Hospital",
                "Man@gmail.com",
                null,
                "Mansheya",
                null,
                null,
                null,
                31.24174,
                29.9674551
            ),
            Emergency_point(
                "Helmeya Hospital",
                "He@gmail.com",
                null,
                "Helmeya",
                null,
                null,
                "123456",
                31.3368367,
                30.1058591
            ),
            Emergency_point(
                "Garden City Hospital",
                "Ga@gmail.com",
                null,
                "Garden City",
                null,
                null,
                "123456",
                31.231694873963278,
                30.03034345
            ),
            Emergency_point(
                "Nasr City Hospital",
                "na@gmail.com",
                null,
                "Nasr City",
                null,
                null,
                "123456",
                31.3726354,
                30.100310899999997
            ),
            Emergency_point(
                "Waily Hospital",
                "Wa@gmail.com",
                null,
                "Waily",
                null,
                null,
                "123456",
                31.2767911,
                30.0580298
            ),
            Emergency_point(
                "Abbaseya Hospital",
                "abb@gmail.com",
                null,
                "Abbaseya",
                null,
                null,
                "123456",
                31.29620330496327,
                30.068072649999998
            ),
            Emergency_point(
                "AinShams University Specialist Hospital",
                "Ain@gmail.com",
                null,
                "Abbaseya",
                null,
                null,
                "123456",
                31.299967,
                30.084648
            ),
            Emergency_point(
                "Italian Hospital",
                "it@gmail.com",
                null,
                "Abbaseya",
                null,
                null,
                "123456",
                31.2822,
                30.0668
            ),

            Emergency_point(
                "Ain Shams Hospital",
                "in@gmaiil.com",
                null,
                "Ain Shams",
                null,
                null,
                "123456",
                31.3253264,
                30.1348193
            ),
            Emergency_point(
                "Sahel Hospital",
                "sah@gmail.com",
                null,
                "Sahel",
                null,
                null,
                "123456",
                31.2406548,
                30.0965672
            ),
            Emergency_point(
                "Red Zone Hospital",
                "redz@gmail.com",
                null,
                "Red Zone",
                null,
                null,
                "123456",
                31.266458206793395,
                30.10177445
            ),
            Emergency_point(
                "Sharabiya Hospital",
                "sharab@gmail.com",
                null,
                "Sharabiya",
                null,
                null,
                "123456",
                31.2603043,
                30.0783665
            ),
            Emergency_point(
                "Rod El-Farag Hospital",
                "rod@gmail.com",
                null,
                "Rod El-Farag",
                null,
                null,
                "123456",
                31.2292698,
                30.0634819
            ),
            Emergency_point(
                "Old Cairo Hospital",
                "oc@gmail.com",
                null,
                "Old Cairo",
                null,
                null,
                "123456",
                31.223496360551223,
                30.01366415
            ),
            Emergency_point(
                "Heliopolis Hospital",
                "help@gmail.com",
                null,
                "Heliopolis",
                null,
                null,
                "123456",
                31.43571254222265,
                30.0207942
            ),
            Emergency_point(
                "Mosky Hospital",
                "mos@gmail.com",
                null,
                "Mosky",
                null,
                null,
                "123456",
                31.2383746,
                30.051386
            ),
            Emergency_point(
                "Khalifa Hospital",
                "Khalifa@gmail.com",
                null,
                "Khalifa",
                null,
                null,
                "123456",
                31.253736542175368,
                30.03403985
            ),
            Emergency_point(
                "Mokattam Hospital",
                "Mokka@g.com",
                null,
                "Mokattam",
                null,
                null,
                "123456",
                31.2431749,
                30.0209068
            ),


            Emergency_point(
                "Bab Al-Shaaria Hospital",
                "bab@gmail.com",
                null,
                "Bab Al-Shaaria",
                null,
                null,
                "123456",
                31.2383746,
                30.051386
            ),
            Emergency_point(
                "Sayeda Zeinab Hospital",
                "sa@gmail.com",
                null,
                "Sayeda Zeinab",
                null,
                null,
                "123456",
                31.23788289124186,
                30.02313795
            ),
            Emergency_point(
                "Manshiet Nasser Hospital",
                "mas@gmail.com",
                null,
                "Manshiet Nasser",
                null,
                null,
                "123456",
                31.283698976243315,
                30.04302365
            )
        )


        val collection = db.collection("Emergency point")
        for (hospital in hospitals) {
            collection.document().set(hospital)
        }
    }

     */
    fun updateEmailAndPassword() {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val hospitals = listOf(
            Emergency_point(
                "Haddayek Al-Qobba Hospital",
                "h@gmail.com",
                null,
                "Haddayek Al-Qobba",
                null,
                null,
                "123456",
                31.2760189,
                30.0736825
            ),
            Emergency_point(
                "Abdeen Hospital",
                "ab@gmail.com",
                null,
                "Abdeen",
                null,
                null,
                "123456",
                31.23766980516448,
                30.054531400000002
            ),
            Emergency_point(
                "Zamalek Hospital",
                "z@gmail.com",
                null,
                "Zamalek",
                null,
                null,
                "123456",
                31.22474054417667,
                30.0446629
            ),
            Emergency_point(
                "Maadi Hospital",
                "M@gmail.com",
                null,
                "Maadi",
                null,
                null,
                "123456",
                31.2429973,
                29.9700229
            ),
            Emergency_point(
                "New Cairo Hospital",
                "NC@gmail.com",
                null,
                "Fifth Settlement",
                null,
                null,
                "123456",
                31.3368939,
                30.1058743
            ),
            Emergency_point(
                "Mohandessin Hospital",
                "moh@gmail.com",
                null,
                "Mohandessin",
                null,
                null,
                "123456",
                31.200597619876515,
                30.05336655
            ),
            Emergency_point(
                "Fifth Settlement Hospital",
                "f@gmail.com",
                null,
                "Fifth Settlement",
                null,
                null,
                "123456",
                31.4181409,
                30.0147462
            ),
            Emergency_point(
                "Giza Hospital",
                "G@gmail.com",
                null,
                "Giza",
                null,
                null,
                "123456",
                31.200597619876515,
                30.05336655
            ),
            Emergency_point(
                "Zeitoun Hospital",
                "Z@gmail.com",
                null,
                "Zeitoun",
                null,
                null,
                "123456",
                31.315370953740157,
                30.1043151
            ),
            Emergency_point(
                "Helwan Hospital",
                "h@gmail.com",
                null,
                "Helwan",
                null,
                null,
                null,
                31.3080686,
                29.7837659
            ),
            Emergency_point(
                "Badr2 Hospital",
                "bad@gmail.com",
                null,
                "Badr City",
                null,
                null,
                "123456",
                31.7233948,
                30.1451471
            ),
            Emergency_point(
                "Shorouk City Hospital",
                "sh@gmail.com",
                null,
                "Shorouk City",
                null,
                null,
                "123456",
                31.7233948,
                30.1451471
            ),
            Emergency_point(
                "Mansheya Hospital",
                "Man@gmail.com",
                null,
                "Mansheya",
                null,
                null,
                null,
                31.24174,
                29.9674551
            ),
            Emergency_point(
                "Helmeya Hospital",
                "He@gmail.com",
                null,
                "Helmeya",
                null,
                null,
                "123456",
                31.3368367,
                30.1058591
            ),
            Emergency_point(
                "Garden City Hospital",
                "Ga@gmail.com",
                null,
                "Garden City",
                null,
                null,
                "123456",
                31.231694873963278,
                30.03034345
            ),
            Emergency_point(
                "Nasr City Hospital",
                "na@gmail.com",
                null,
                "Nasr City",
                null,
                null,
                "123456",
                31.3726354,
                30.100310899999997
            ),
            Emergency_point(
                "Waily Hospital",
                "Wa@gmail.com",
                null,
                "Waily",
                null,
                null,
                "123456",
                31.2767911,
                30.0580298
            ),
            Emergency_point(
                "Abbaseya Hospital",
                "abb@gmail.com",
                null,
                "Abbaseya",
                null,
                null,
                "123456",
                31.29620330496327,
                30.068072649999998
            ),
            Emergency_point(
                "AinShams University Specialist Hospital",
                "Ain@gmail.com",
                null,
                "Abbaseya",
                null,
                null,
                "123456",
                31.299967,
                30.084648
            ),
            Emergency_point(
                "Italian Hospital",
                "it@gmail.com",
                null,
                "Abbaseya",
                null,
                null,
                "123456",
                31.2822,
                30.0668
            ),

            Emergency_point(
                "Ain Shams Hospital",
                "in@gmaiil.com",
                null,
                "Ain Shams",
                null,
                null,
                "123456",
                31.3253264,
                30.1348193
            ),
            Emergency_point(
                "Sahel Hospital",
                "sah@gmail.com",
                null,
                "Sahel",
                null,
                null,
                "123456",
                31.2406548,
                30.0965672
            ),
            Emergency_point(
                "Red Zone Hospital",
                "redz@gmail.com",
                null,
                "Red Zone",
                null,
                null,
                "123456",
                31.266458206793395,
                30.10177445
            ),
            Emergency_point(
                "Sharabiya Hospital",
                "sharab@gmail.com",
                null,
                "Sharabiya",
                null,
                null,
                "123456",
                31.2603043,
                30.0783665
            ),
            Emergency_point(
                "Rod El-Farag Hospital",
                "rod@gmail.com",
                null,
                "Rod El-Farag",
                null,
                null,
                "123456",
                31.2292698,
                30.0634819
            ),
            Emergency_point(
                "Old Cairo Hospital",
                "oc@gmail.com",
                null,
                "Old Cairo",
                null,
                null,
                "123456",
                31.223496360551223,
                30.01366415
            ),
            Emergency_point(
                "Heliopolis Hospital",
                "help@gmail.com",
                null,
                "Heliopolis",
                null,
                null,
                "123456",
                31.43571254222265,
                30.0207942
            ),
            Emergency_point(
                "Mosky Hospital",
                "mos@gmail.com",
                null,
                "Mosky",
                null,
                null,
                "123456",
                31.2383746,
                30.051386
            ),
            Emergency_point(
                "Khalifa Hospital",
                "Khalifa@gmail.com",
                null,
                "Khalifa",
                null,
                null,
                "123456",
                31.253736542175368,
                30.03403985
            ),
            Emergency_point(
                "Mokattam Hospital",
                "Mokka@g.com",
                null,
                "Mokattam",
                null,
                null,
                "123456",
                31.2431749,
                30.0209068
            ),


            Emergency_point(
                "Bab Al-Shaaria Hospital",
                "bab@gmail.com",
                null,
                "Bab Al-Shaaria",
                null,
                null,
                "123456",
                31.2383746,
                30.051386
            ),
            Emergency_point(
                "Sayeda Zeinab Hospital",
                "sa@gmail.com",
                null,
                "Sayeda Zeinab",
                null,
                null,
                "123456",
                31.23788289124186,
                30.02313795
            ),
            Emergency_point(
                "Manshiet Nasser Hospital",
                "mas@gmail.com",
                null,
                "Manshiet Nasser",
                null,
                null,
                "123456",
                31.283698976243315,
                30.04302365
            )
        )

        hospitals.forEach { hospital ->
            // Update email and password fields

firestore.collection("Emergency point").document().set(hospital)
            // Register email and password in Firebase Authentication
            if (hospital.email!=null &&hospital.passwords!=null) {
                auth.createUserWithEmailAndPassword(hospital.email!!, hospital.passwords!!)
            }
}}}
















