package com.example.emergency_alert_system.Doctor.WatingList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.Creation.Doctor
import com.example.emergency_alert_system.Doctor.model.waitingList
import com.example.emergency_alert_system.user.AssignDoctor.searchAdapter
import com.example.emergency_alert_system.user.medicines.medicineAdapter
import com.example.emergency_alert_system.user.model.medicine
import com.example.emergencyalertsystem.R

class waitingListAdapter (var waitingList: List<waitingList>): RecyclerView.Adapter<waitingListAdapter.waitingListViewHolder>() {
    class waitingListViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
        val patientName: TextView =itemView.findViewById(R.id.patient_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): waitingListViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.waiting_list_card,parent,false)
        return waitingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: waitingListViewHolder, position: Int) {
        val watingList: waitingList =waitingList[position]
        holder.patientName.text=watingList.patient_name

    }

    override fun getItemCount(): Int {
        return waitingList.size
    }

}