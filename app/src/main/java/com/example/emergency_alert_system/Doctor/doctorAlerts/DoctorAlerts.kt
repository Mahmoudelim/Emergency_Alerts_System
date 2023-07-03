package com.example.emergency_alert_system.Doctor.doctorAlerts

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.myPatients.patientAdapter
import com.example.emergency_alert_system.Doctor.patientofdr
import com.example.emergency_alert_system.EMP.home.RequestAdapter
import com.example.emergency_alert_system.EMP.model.Alerts
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorAlerts.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorAlerts : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    var RequestsList: ArrayList<String>? = null
    lateinit var RequestAdapter: DocRequestAdapter
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
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
        return inflater.inflate(R.layout.fragment_doctor_alerts, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DoctorAlerts.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DoctorAlerts().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RequestsList = arrayListOf<String>()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.docrequests_recycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        RequestAdapter = DocRequestAdapter(RequestsList!!)
        recyclerView.adapter = RequestAdapter

        RequetsFromFirestore()

        RequestAdapter.setOnItemClickListner(object : DocRequestAdapter.onItemClickListner {
            override fun onClick(position: Int) {
                val patientName = RequestsList!![position]

                val intent = Intent(requireContext(), patientofdr::class.java)
                intent.putExtra("pat2", patientName)
                startActivity(intent)
            }
        })
    }


    private fun RequetsFromFirestore() {
        var mAuth: FirebaseAuth
        var firestore:FirebaseFirestore
        mAuth= FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        val UID =mAuth.currentUser!!.uid
        val currentdocref=firestore.collection("Doctor".trim()).document(UID).get().addOnSuccessListener { document ->

            val nm: String? = document.data!!["name".trim()].toString()
            if (nm != null) {
                firestore.collection("doctorEmergencies".trim()).document(nm).get().addOnSuccessListener { document ->
                    val patientName:String=document.data!!["UserName".trim()].toString()
                    Toast.makeText(this.context, "$patientName", Toast.LENGTH_SHORT).show()
                    RequestsList!!.add(patientName)
                    RequestAdapter.notifyDataSetChanged()
            }

            }

    }
}}