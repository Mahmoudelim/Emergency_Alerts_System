package com.example.emergency_alert_system.Doctor.myPatients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.WatingList.waitingListAdapter
import com.example.emergency_alert_system.Doctor.model.Mypatients
import com.example.emergency_alert_system.Doctor.model.waitingList
import com.example.emergency_alert_system.user.creation.user
import com.example.emergencyalertsystem.R

class patientAdapter(var patientList: List<Mypatients>): RecyclerView.Adapter<patientAdapter.patientViewHolder>() {
    class patientViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val patientName: TextView =itemView.findViewById(R.id.patientname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): patientViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.patient_card,parent,false)
        return patientViewHolder(view)
    }

    override fun onBindViewHolder(holder: patientViewHolder, position: Int) {
        val patientList: Mypatients =patientList[position]
        holder.patientName.text=patientList.PATIENTS.toString()

    }

    override fun getItemCount(): Int {
        return patientList.size
    }

}
