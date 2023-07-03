package com.example.emergency_alert_system.user.EditInfo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.emergency_alert_system.MIddle_Layer.Request
import com.example.emergencyalertsystem.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_edit_info.*
import org.checkerframework.checker.nullness.qual.NonNull

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditInfo : Fragment() {
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
        return inflater.inflate(R.layout.fragment_edit_info, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditInfo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         var Email:String?=null
        var pass:String?=null
        var phone:String?=null
        var naighbourhood:String?=null
        var streetname:String?=null
        var Buildingnum:String?=null
        var  flatnumber:String?=null
    var docname:String?=null
var reque:Request=Request()
        var mAuth: FirebaseAuth
        var firestore: FirebaseFirestore
        mAuth= FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()
        val UID =mAuth.currentUser!!.uid

        val textwatcher=object :TextWatcher{
              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                     // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()


              }

               override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                  // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()
               }

               override fun afterTextChanged(s: Editable?) {
                   val newemail: String?=email_edit_text.text.toString()

                      // .signInWithEmailAndPassword('you@domain.example', 'correcthorsebatterystaple')
                       //.then(funuserCredential) {


                       val userref=firestore.collection("USERS".trim()).document(UID).update("email".trim(),newemail)
                  // Toast.makeText(context, " your email set to : $newemail ", Toast.LENGTH_SHORT).show()
              }

          }
        email_edit_text.addTextChangedListener(textwatcher)
        val textwatcher2=object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(s: Editable?) {
                val newphone: String?=phone_edit_text.text.toString()
                val userref=firestore.collection("USERS".trim()).document(UID).update("phone_num".trim(),newphone)
                //Toast.makeText(context, " your phone set to : $newphone ", Toast.LENGTH_SHORT).show()
            }

        }
        phone_edit_text.addTextChangedListener(textwatcher2)
        val flatnumwatcher=object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(s: Editable?) {
                val userref = firestore.collection("USERS".trim()).document(UID).get()
                    .addOnSuccessListener { document ->

                        val nm: String? = document.data!!["username".trim()].toString()
                        val newemail: String? =flat_number_edit_text.text.toString()
                        val userref =firestore .collection("USERS Adresses").document("${nm}:Address ".trim())
                            .update("flatingnumb".trim(), newemail)

                    }
            }
        }
        flat_number_edit_text.addTextChangedListener(flatnumwatcher)
        val buildnumwatcher=object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(s: Editable?) {
                val userref = firestore.collection("USERS".trim()).document(UID).get()
                    .addOnSuccessListener { document ->

                        val nm: String? = document.data!!["username".trim()].toString()
                        val newemail: String? =  building_number_edit_text.text.toString()
                        val userref =firestore .collection("USERS Adresses").document("${nm}:Address ".trim())
                            .update("buildingnumb".trim(), newemail)
                    //    Toast.makeText(
                      //      context,
                        //    " your streetname set to : $newemail ",
                          //  Toast.LENGTH_SHORT
                        //).show()
                    }
            }
        }
        building_number_edit_text.addTextChangedListener(buildnumwatcher)
        val streetwatcher=object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(s: Editable?) {
                val userref = firestore.collection("USERS".trim()).document(UID).get()
                    .addOnSuccessListener { document ->

                        val nm: String? = document.data!!["username".trim()].toString()
                        val newstreet: String? = streetname_edit_text.text.toString()
                        val userref =firestore .collection("USERS Adresses").document("${nm}:Address ".trim())

                            .update("streetname".trim(), newstreet)
                       /* Toast.makeText(
                            context,
                            " your naighbrhoood set to : $newstreet ",
                            Toast.LENGTH_SHORT
                        ).show()

                        */

                    }
            }
        }
        streetname_edit_text.addTextChangedListener(streetwatcher)
        val naigbwatcher=object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(s: Editable?) {
                val userref = firestore.collection("USERS".trim()).document(UID).get()
                    .addOnSuccessListener { document ->

                        val nm: String? = document.data!!["username".trim()].toString()
                        val newemail: String? = neighborhood_edit_text.text.toString()
                        val userref =firestore .collection("USERS Adresses").document("${nm}:Address ".trim())
                            .update("naighbourrhood".trim(), newemail)
                        /*Toast.makeText(
                            context,
                            " your naighbrhoood set to : $newemail ",
                            Toast.LENGTH_SHORT
                        ).show()

                         */
                    }
            }
        }
        neighborhood_edit_text.addTextChangedListener(naigbwatcher)




        val userref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->

            val nm: String? = document.data!!["username".trim()].toString()
            val email: String? = document.data!!["email".trim()].toString()
            email_edit_text.setText(email,TextView.BufferType.EDITABLE)
            val phone: String? = document.data!!["phone_num".trim()].toString()
            phone_edit_text.setText(phone,TextView.BufferType.EDITABLE)
            val doc: String? = document.data!!["user_docname".trim()].toString()
            docname_edit_textori.setText("Dr.$doc")
            if (doc ==null || doc==""){
                docname_edit_textori.setVisibility(View.INVISIBLE);
            }

            val useraddress=firestore.collection("USERS Adresses").document("${nm}:Address ".trim()).get().addOnSuccessListener { document ->
        val naighb:String? = document.data!!["naighbourrhood".trim()].toString()
                neighborhood_edit_text.setText(naighb,TextView.BufferType.EDITABLE)
        val streetnm:String? = document.data!!["streetname".trim()].toString()
                streetname_edit_text.setText(streetnm,TextView.BufferType.EDITABLE)
         val buildnm:String? = document.data!!["buildingnumb".trim()].toString()
                building_number_edit_text.setText(buildnm,TextView.BufferType.EDITABLE)
         val flatnm:String? = document.data!!["flatingnumb".trim()].toString()
                flat_number_edit_text.setText(flatnm,TextView.BufferType.EDITABLE)
            }

        }
//if theres no doctor assign hidden the edittext



         }}
