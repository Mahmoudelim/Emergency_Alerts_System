package com.example.emergency_alert_system.Doctor.myPatients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emergency_alert_system.Doctor.model.Mypatients
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
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
  private fun getpatientlist(){
      var mAuth: FirebaseAuth
      mAuth= FirebaseAuth.getInstance()
      var firestore:FirebaseFirestore
      firestore= FirebaseFirestore.getInstance()
      val UID =mAuth.currentUser!!.uid
      val docref=firestore.collection("/Doctor").document(UID).get().addOnSuccessListener { document ->
          val nm: String? = document.data!!["name"].toString().trim()
      firestore.collection("Doctor patients").document("$nm PATIENTS").get().addOnCompleteListener { task ->
          if (task.isSuccessful) {
             val document = task.result as Mypatients
          }

      }
      }
}

}