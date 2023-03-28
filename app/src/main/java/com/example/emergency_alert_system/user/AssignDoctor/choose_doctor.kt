package com.example.emergency_alert_system.user.AssignDoctor


import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.Creation.Doctor
import com.example.emergency_alert_system.MIddle_Layer.Request
import com.example.emergency_alert_system.user.creation.user_Login
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_choose_doctor.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [choose_doctor.newInstance] factory method to
 * create an instance of this fragment.
 */
class choose_doctor : Fragment() {
   // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    lateinit var firestore: FirebaseFirestore
    private var searchList:MutableList<Doctor> = mutableListOf()
    var searchAdapter=searchAdapter(searchList)
    private var req:Request= Request()
    private var currentuser:user_Login= user_Login()
    private var cur:String=currentuser.getcurrentuser()
    private lateinit var mAuth: FirebaseAuth
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
        return inflater.inflate(R.layout.fragment_choose_doctor, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment choose_doctor.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            choose_doctor().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recycler
        mAuth=FirebaseAuth.getInstance()
        doctors_recycle.hasFixedSize()
        doctors_recycle.layoutManager= LinearLayoutManager(this.context)
        //searchAdapter=searchAdapter(searchList)
        doctors_recycle.adapter=searchAdapter
        searchAdapter.setOnItemClickListner(object : searchAdapter.onItemClickListner{
            override fun onClick(position: Int) {
               // val nm:String?=null
                mAuth= FirebaseAuth.getInstance()
                firestore=FirebaseFirestore.getInstance()
                val UID =mAuth.currentUser!!.uid
                val docref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->

                    val nm: String? = document.data!!["username".trim()].toString()


                    val doc_name: String? = searchList[position].Name
                    Toast.makeText(
                        context,
                        "you clicked on item no $doc_name && user $nm",
                        Toast.LENGTH_SHORT
                    ).show()
                    val bundle=Bundle()
                    bundle.putString("docName",doc_name)
                    bundle.putString("userName",nm)
                    view!!.findNavController().navigate(R.id.action_choose_doctor_to_selectDoctor,bundle)
                }
            }

        } )

        //searchView
        doct_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // get value from text
                val searchText:String= doct_search.text.toString().trim()
                searcInFirestore(searchText)

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })


    }
    private fun searcInFirestore(searchText: String) {
        firestore=FirebaseFirestore.getInstance()
        firestore.collection("Doctor")
            .orderBy("name").startAt(searchText).endAt("$searchText\uf8ff")
            .get().addOnCompleteListener{
                if (it.isSuccessful)
                {
                    searchList=it.result!!.toObjects(Doctor::class.java)
                    searchAdapter.searcList=searchList
                    searchAdapter.notifyDataSetChanged()

                }
                else{
                    Log.d(ContentValues.TAG,"${it.exception!!.message}")
                    Toast.makeText(this.context,"Error", Toast.LENGTH_SHORT)
                }
            }

    }

}