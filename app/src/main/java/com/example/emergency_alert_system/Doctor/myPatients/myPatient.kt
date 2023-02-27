package com.example.emergency_alert_system.Doctor.myPatients

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.WatingList.waitingListAdapter
import com.example.emergency_alert_system.Doctor.model.Mypatients
import com.example.emergency_alert_system.user.creation.user_midical_info
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [myPatient.newInstance] factory method to
 * create an instance of this fragment.
 */
class myPatient : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var patientList: MutableList<String>?=null
    lateinit var recyclerView: RecyclerView
     lateinit var ptientlistadapter:patientAdapter
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
        return inflater.inflate(R.layout.fragment_my_patient, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment myPatient.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            myPatient().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patientList= arrayListOf<String>()
        val layoutManager= LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.patientrecycle)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        ptientlistadapter= patientAdapter(patientList!!)
        recyclerView.adapter=ptientlistadapter
getpatientlist()
    }
  private fun getpatientlist(){
      var mAuth:FirebaseAuth
      var firestore:FirebaseFirestore
      mAuth= FirebaseAuth.getInstance()
      firestore=FirebaseFirestore.getInstance()
      val UID =mAuth.currentUser!!.uid
      val currentdocref=firestore.collection("Doctor".trim()).document(UID).get().addOnSuccessListener { document ->

          val nm: String? = document.data!!["name".trim()].toString()
      val docref= firestore.collection("/Doctor patients").document("/$nm PATIENTS")
          docref.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->

              if (e != null) {
                  Log.w(ContentValues.TAG, "Listen failed.", e)
                  return@EventListener
              }

              if (documentSnapshot != null && documentSnapshot.exists()) {

                  docref.get().addOnSuccessListener { documentSnapshot ->
                      val userInfo = documentSnapshot.toObject(Mypatients::class.java)
                      if (userInfo != null) {
                          userInfo.PATIENTS!!.forEach {
                              Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                              patientList!!.add(it)

                          }



      }


}}})}}}