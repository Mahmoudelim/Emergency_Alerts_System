package com.example.emergency_alert_system.user.AssignDoctor

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.icu.text.StringSearch
import android.nfc.Tag
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emergency_alert_system.Doctor.Creation.Doctor
import com.example.emergencyalertsystem.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_assign_doctor.*


@Suppress("UNREACHABLE_CODE")
class assign_doctor : Fragment() {

  private val firestore:FirebaseFirestore=FirebaseFirestore.getInstance()
  private var searchList:List<Doctor> =ArrayList()
  private val searchAdapter=searchAdapter(searchList)
  lateinit var doctor:Doctor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign_doctor, container, false)
        //recyclerView control
        doctors_recycle.layoutManager=LinearLayoutManager(this.context)
        doctors_recycle.adapter=searchAdapter

        //searchView
        doc_search.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               // get value from text
                val searchText:String= doc_search.text.toString()
                searcInFirestore(searchText.toLowerCase())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searcInFirestore(searchText: String) {
      firestore.collection("Doctor").orderBy("Name").startAt(searchText).endAt("$searchText\uf8ff")
         .get().addOnCompleteListener{
              if (it.isSuccessful)
              {
                   searchList=it.result!!.toObjects(Doctor::class.java)
                   searchAdapter.searcList=searchList
                   searchAdapter.notifyDataSetChanged()
              }
              else{
                   Log.d(TAG,"${it.exception!!.message}")
                  Toast.makeText(this.context,"Error",Toast.LENGTH_SHORT)
              }
          }

    }

}