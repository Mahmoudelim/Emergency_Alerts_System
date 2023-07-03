package com.example.emergency_alert_system.EMP.Medical_Info
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.EMP.RequestAcceptance.myArray
import com.example.emergency_alert_system.EMP.RequestAcceptance.relativesPhone
import com.example.emergency_alert_system.EMP.home.RequestAdapter
import com.example.emergency_alert_system.EMP.model.Alerts
import com.example.emergencyalertsystem.R
import com.google.firebase.firestore.FirebaseFirestore
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [medicalInfoForAcceptancHe.newInstance] factory method to
 * create an instance of this fragment.
 */
class medicalInfoForAcceptance : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    var ChronicList: ArrayList<String>?= null
    var chronicListAdapter: ChronicListAdapter? =null
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medical_info_for_acceptance, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment medicalInfoForAcceptance.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                medicalInfoForAcceptance().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.chronic_recycle)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        // Initialize chronicList with an empty ArrayList
        ChronicList = arrayListOf()

        // Retrieve user's medical info document from Firestore
        val username: String = requireArguments().getString("username".trim()).toString()

        val medicalInfoRef = firestore.collection("USERS MEDICAL INFO")
        val q = medicalInfoRef.whereEqualTo("username", username)

        q.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (documentSnapshot in document.documents) {
                        // Get the array field from the document
                        val list = documentSnapshot.get("choronic") as ArrayList<String>
                        Toast.makeText(context, "Array from document ${documentSnapshot.id}: $list", Toast.LENGTH_SHORT).show()
                        Log.i("chronicList", list.toString())
                        // Add the array elements to the chronicList
                        ChronicList!!.addAll(list)
                    }
                    chronicListAdapter = ChronicListAdapter(ChronicList!!)
                    recyclerView.adapter = chronicListAdapter
                    chronicListAdapter!!.notifyDataSetChanged()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting document: ", exception)
            }
    }

}