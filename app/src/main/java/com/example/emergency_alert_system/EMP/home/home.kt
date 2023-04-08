package com.example.emergency_alert_system.EMP.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.EMP.creation.Emergency_point
import com.example.emergency_alert_system.EMP.model.Alerts
import com.example.emergency_alert_system.user.AlertMaking.nm
import com.example.emergency_alert_system.user.creation.user_location
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 */
var Ep_name=""
class home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    var RequestsList: ArrayList<Alerts>? = null
    lateinit var RequestAdapter: RequestAdapter
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
        return inflater.inflate(R.layout.fragment_home3, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RequestsList = arrayListOf<Alerts>()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.requests_recycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        RequestAdapter = RequestAdapter(RequestsList!!)
        recyclerView.adapter = RequestAdapter

        RequetsFromFirestore()
    }

    private fun RequetsFromFirestore() {
        val mAuth = FirebaseAuth.getInstance()
        val uid = mAuth.currentUser!!.uid
        Log.i("displayNamee", uid)

        val DocRef = firestore.collection("Emergency point").document(uid)
            .get().addOnSuccessListener { document ->
                Log.i("doccc", document.data.toString())
                if (document != null) {
                    Ep_name = document.getString("epname").toString()
                    if (Ep_name != null) {
                        Log.i("EppName", Ep_name)

                        // Move the Firestore listener to this block to make sure it's only added once
                        firestore.collection("$Ep_name requests").addSnapshotListener(
                            object : EventListener<QuerySnapshot> {
                                override fun onEvent(
                                    value: QuerySnapshot?,
                                    error: FirebaseFirestoreException?
                                ) {
                                    context?.let {
                                        if (error != null) {
                                            Toast.makeText(
                                                it,
                                                "fire store error",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return
                                        }
                                    }
                                    for (dc: DocumentChange in value?.documentChanges!!) {
                                        if (dc.type == DocumentChange.Type.ADDED) {
                                            RequestsList!!.add(dc.document.toObject(Alerts::class.java))
                                            Log.i("catched", RequestsList.toString())
                                        }
                                    }
                                    RequestAdapter.notifyDataSetChanged()
                                }
                            })
                    }
                }
            }
        Log.i("currentEP", Ep_name)
    }}