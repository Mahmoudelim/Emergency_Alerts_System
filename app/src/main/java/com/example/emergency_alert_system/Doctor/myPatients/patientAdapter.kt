package com.example.emergency_alert_system.Doctor.myPatients

import android.os.Bundle
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

class patientAdapter(var patientList: List<String>): RecyclerView.Adapter<patientAdapter.patientViewHolder>() {
    private lateinit var mlistner: onItemClickListner


    interface onItemClickListner{
        fun onClick(position: Int)


    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mlistner=listner
    }

    class patientViewHolder (itemView: View,listner: patientAdapter.onItemClickListner): RecyclerView.ViewHolder(itemView) {
        val patientName: TextView =itemView.findViewById(R.id.patient_name22)
        init {
            itemView.setOnClickListener {
                listner.onClick(adapterPosition)

            }

        }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): patientViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.patient_card,parent,false)
        return patientViewHolder(view,mlistner)
    }

    override fun onBindViewHolder(holder: patientViewHolder, position: Int) {
        val patientList: String =patientList[position]
        holder.patientName.text=patientList.toString()
        val bundle= Bundle()
        bundle.putString("patientname",holder.patientName.text.toString())

    }

    override fun getItemCount(): Int {
        return patientList.size
    }

}
