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
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.Creation.Doctor
import com.example.emergencyalertsystem.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_assign_doctor.*


@Suppress("UNREACHABLE_CODE")
class assign_doctor : Fragment() {
    lateinit var recyclerView: RecyclerView
  lateinit var firestore:FirebaseFirestore
  private var searchList:MutableList<Doctor> = mutableListOf()
  private val searchAdapter=searchAdapter(searchList)
  lateinit var doctor:Doctor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign_doctor, container, false)
        firestore=FirebaseFirestore.getInstance()
        //recyclerView control

        val text: String=doc_search.text.toString()
        betn.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
            searcInFirestore(text)
            Toast.makeText(context,"tmam",Toast.LENGTH_SHORT)
            }})


    }
    @SuppressLint("NotifyDataSetChanged")
    private fun searcInFirestore(searchText: String) {
      firestore.collection("Doctor")
          .orderBy("Name").startAt(searchText)
         .get().addOnCompleteListener{
              if (it.isSuccessful)
              {
                   searchtext.text=it.result!!.toObjects(Doctor::class.java).toString()


              }
              else{
                   Log.d(TAG,"${it.exception!!.message}")
                  Toast.makeText(this.context,"Error",Toast.LENGTH_SHORT)
              }
          }

    }

}