package com.example.emergency_alert_system.user.medicines

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.model.waitingList
import com.example.emergency_alert_system.user.creation.user_Login
import com.example.emergency_alert_system.user.creation.user_general_info
import com.example.emergency_alert_system.user.creation.user_midical_info
import com.example.emergency_alert_system.user.model.medicine

import com.example.emergencyalertsystem.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import org.checkerframework.checker.nullness.qual.NonNull

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [medicines.newInstance] factory method to
 * create an instance of this fragment.
 */
class medicines : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    var medicineList: ArrayList<medicine>? = null
    lateinit var medicineAdapter: medicineAdapter
    var db = FirebaseFirestore.getInstance()

    var user: user_Login = user_Login()
    var us: user_general_info = user_general_info()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicines, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment medicines.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            medicines().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
var medicine1:medicine= medicine()
        var medicine2:medicine=medicine()
        var medicine3:medicine=medicine()
        medicineList = arrayListOf<medicine>()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.medicines_recycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        medicineAdapter = medicineAdapter(medicineList!!)
        recyclerView.adapter = medicineAdapter
        Toast.makeText(context, "${medicineList}", Toast.LENGTH_LONG).show()
        var mAuth:FirebaseAuth
        var firestore:FirebaseFirestore
        mAuth= FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        val UID =mAuth.currentUser!!.uid


        val userref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->

            val nm: String? = document.data!!["username".trim()].toString()



       val docref= db.collection("/USERS MEDICAL INFO").document("/$nm:medical info")
       docref.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@EventListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {

                    docref.get().addOnSuccessListener { documentSnapshot ->
                        val userInfo = documentSnapshot.toObject(user_midical_info::class.java)
                        if (userInfo != null) {
                            userInfo.medicines!!.forEach {
                                Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                                medicineList!!.add(it)
                            }
/*
                            val usermedicine=userInfo.medicines
                            val m1=userInfo.medicines?.get(0)
                            val m2=userInfo.medicines?.get(1)
                            val m3=userInfo.medicines?.get(2)



                            medicine1.medicine_name=m1!!.medicine_name
                           medicine1.assignedWith=m1.assignedWith
                            medicine1.medicine_time=m1.medicine_time
                            medicine2.medicine_name=m2!!.medicine_name
                            medicine2.assignedWith=m2.assignedWith
                            medicine2.medicine_time=m2.medicine_time
                            medicine3.medicine_name=m3!!.medicine_name
                            medicine3.assignedWith=m3.assignedWith
                            medicine3.medicine_time=m3.medicine_time



                            medicineList!!.add(medicine1)
                            medicineList!!.add(medicine2)
                            medicineList!!.add(medicine3)



 */



                       }
                    }
                }
                })
                //val document=task.result.get("medicines")





                    /*
                val name1=task.result.data!!["/0 medicine_name"].toString()
                    val assign=task.result.data!!["/0 assignedWith"].toString()
                    val time=task.result.data!!["/0 medicine_time"].toString()
medicine1.medicine_name=name1
                    medicine1.medicine_time=time
                    medicine1.assignedWith=assign
medicineList!!.add(medicine1)

                     */

                }







    }

    /* private fun medicineFromFireStore() { // reterive medicine document for this user from fire store
        var medicine:medicine=medicine()
        db.collection("/USERS MEDICAL INFO").whereEqualTo("username".trim(), username.trim())
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result){
                        val name=document.data!!["medicines"] as medicine
                        medicineList!!.add(name)
                    }
                     docRef.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->

    if (e != null) {
            Log.w(TAG, "Listen failed.", e)
            return@EventListener
        }

        if (documentSnapshot != null && documentSnapshot.exists()) {

            docRef.get().addOnSuccessListener { documentSnapshot ->
                val userInfo = documentSnapshot.toObject(UserInfo::class.java)

                }


            }}
               }
    */
}
