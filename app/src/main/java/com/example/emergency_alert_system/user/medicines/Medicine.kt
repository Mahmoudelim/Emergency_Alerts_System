package com.example.emergency_alert_system.user.medicines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.user.model.medicine
import com.example.emergencyalertsystem.R
import com.google.firebase.firestore.FirebaseFirestore


class Medicine : Fragment() {
 lateinit var recyclerView: RecyclerView
 lateinit var medicineList: List<medicine>
 lateinit var medicineAdapter: medicineAdapter
 lateinit var firestore: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false)
              recyclerView=recyclerView.findViewById(R.id.medicine_recycler)
              recyclerView.layoutManager=LinearLayoutManager(this.context)
              recyclerView.setHasFixedSize(true)
              medicineList= arrayListOf()
              medicineAdapter= medicineAdapter(medicineList)
              medicineFromFireStore()
    }

    private fun medicineFromFireStore() {
      // reterive medicine document for this user from fire store



    }


}