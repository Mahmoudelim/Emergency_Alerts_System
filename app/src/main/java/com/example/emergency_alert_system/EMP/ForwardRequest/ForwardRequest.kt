package com.example.emergency_alert_system.EMP.ForwardRequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.emergency_alert_system.user.AlertMaking.nm
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForwardRequest.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForwardRequest : Fragment() {
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
        return inflater.inflate(R.layout.fragment_forward_request, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForwardRequest.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForwardRequest().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val edit= view.findViewById<EditText>(R.id.EP_forward)
        val fepname=edit.text.toString()
        val button = view.findViewById<Button>(R.id.EP_forward_btn)

        button.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                /*
                val bundle=Bundle()
                bundle.putString("street name",holder.street_name.text.toString())
                bundle.putString("age",holder.age.text.toString())
                bundle.putString("userName",holder.Alert_name.text.toString())

                 */
                var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
                val userName :String=requireArguments().getString("userName").toString()
                val streetname :String=requireArguments().getString("street name".trim()).toString()
                val age :String=requireArguments().getString("age".trim()).toString()
                val alertMap = hashMapOf(
                    "user_name" to userName,
                    "user_age" to age ,
                    "street_name" to streetname
                )
                firestore.collection("$fepname requests").document("$userName request").set(alertMap)
            }

        })}
}