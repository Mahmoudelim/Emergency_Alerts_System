package com.example.emergency_alert_system.user.medicines

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.user.creation.user_Login
import com.example.emergency_alert_system.user.creation.user_general_info
import com.example.emergency_alert_system.user.model.medicine
import com.example.emergencyalertsystem.R
import com.google.firebase.firestore.*

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
     var medicineList: ArrayList<medicine>?=null
    lateinit var medicineAdapter: medicineAdapter
    var db = FirebaseFirestore.getInstance()
    var username:String=""
     var user:user_Login= user_Login()
var us:user_general_info=user_general_info()

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
        medicineList= arrayListOf<medicine>()
        medicineFromFireStore()

        username=user!!.getcurrentuser().toString().trim()


        val layoutManager=LinearLayoutManager(context)
        recyclerView=view.findViewById(R.id.medicines_recycler)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
        medicineAdapter=medicineAdapter(medicineList!!)
        recyclerView.adapter=medicineAdapter

    }

    private fun medicineFromFireStore() {
        // reterive medicine document for this user from fire store
        var medicine:medicine=medicine()
        db.collection("USERS MEDICAL INFO".trim()).whereEqualTo("username".trim(), username.trim())

            .addSnapshotListener(object: EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ){
                    if (error!=null)
                    {
                        Log.e("FireStore Error",error.message.toString())
                        Toast.makeText(context,"Error", Toast.LENGTH_SHORT)
                        return
                    }

                    for(dc: DocumentChange in value?.documentChanges!!)
                    {
                        if (dc.type== DocumentChange.Type.ADDED){
                            medicine.medicine_name= dc.document!!.data["medicine_name"].toString()
                        medicine.assignedWith=dc.document!!.data["assignedWith"].toString()
                        medicine.medicine_time=dc.document!!.data["medicine_time"].toString()
                       // medicine.username=""
                        medicineList!!.add(medicine )
                           // medicineList!!.add(dc.document.toObject(medicine::class.java))
                        }
                    }
                    medicineAdapter.notifyDataSetChanged()
                    Toast.makeText(context,"sucsses", Toast.LENGTH_SHORT)

                }
            })

    }
}