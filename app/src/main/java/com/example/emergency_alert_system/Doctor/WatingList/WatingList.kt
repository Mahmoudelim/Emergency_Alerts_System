package com.example.emergency_alert_system.Doctor.WatingList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.Creation.doctor_login
import com.example.emergency_alert_system.Doctor.model.waitingList
import com.example.emergency_alert_system.MIddle_Layer.Request
import com.example.emergency_alert_system.user.creation.user_midical_info
import com.example.emergency_alert_system.user.medicines.medicineAdapter
import com.example.emergency_alert_system.user.model.medicine
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.waiting_list_card.*
import java.util.Objects

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WatingList.newInstance] factory method to
 * create an instance of this fragment.
 */
class WatingList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    var watingList: MutableList<String?>?=null
    lateinit var waitingListAdapter: waitingListAdapter
    lateinit var firestore: FirebaseFirestore

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
        return inflater.inflate(R.layout.fragment_wating_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WatingList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WatingList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watingList= arrayListOf<String?>()
        //watingListFromFireStore()
         var request_deticion:Request=Request()
        var mAuth: FirebaseAuth
        mAuth= FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        val UID =mAuth.currentUser!!.uid
        val docref=firestore.collection("/Doctor").document(UID).get().addOnSuccessListener { document ->
            val nm: String? = document.data!!["name"].toString().trim()

            val mylist =  firestore.collection("/Doctor:$nm  _Request_list")
                .get().addOnSuccessListener {
                    for(doc in it){
                        val taskmodel:String= doc.data!!["usern"].toString()
                        watingList!!.add(taskmodel)}



        val layoutManager= LinearLayoutManager(context)

        recyclerView=view.findViewById(R.id.waitingList_recycle)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        waitingListAdapter=waitingListAdapter(watingList!!)
        recyclerView.adapter=waitingListAdapter
        waitingListAdapter.setOnItemClickListner(object:waitingListAdapter.onItemClickListner{
                        override fun onClick(position: Int) {
                           val pname= (watingList as ArrayList<String?>)[position]
                           request_deticion.approved(pname!!,nm!!)
                            Toast.makeText(context , " $pname",Toast.LENGTH_SHORT).show()
                        }

                    })
                  /*  button.setOnClickListener {
                        request_deticion.approved(pname!!,nm!!)
                        Toast.makeText(context , " $pname",Toast.LENGTH_SHORT).show()
                    }


                   */
    }
/*
   private fun watingListFromFireStore() {
       var mAuth: FirebaseAuth
       mAuth= FirebaseAuth.getInstance()
       firestore=FirebaseFirestore.getInstance()
       val UID =mAuth.currentUser!!.uid
       val docref=firestore.collection("/Doctor").document(UID).get().addOnSuccessListener { document ->
           val nm: String = document.data!!["name"].toString().trim()

           val mylist =  firestore.collection("/Doctor:$nm  _Request_list")
               .get().addOnSuccessListener {
                   for(doc in it){
                       val taskmodel:String= doc.data!!["usern"].toString()
                       watingList!!.add(taskmodel)}


         }





 */




    }}}
