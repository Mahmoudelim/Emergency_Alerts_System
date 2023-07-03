package com.example.emergency_alert_system.Doctor.doctorAlerts



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.myPatients.patientAdapter
import com.example.emergency_alert_system.EMP.model.Alerts
import com.example.emergencyalertsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DocRequestAdapter(var AlertList: ArrayList<String>):RecyclerView.Adapter<DocRequestAdapter.AlertViewHolder>() {

    private lateinit var mlistner: onItemClickListner


    interface onItemClickListner{
        fun onClick(position: Int)


    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mlistner=listner
    }

    class AlertViewHolder(itemView: View,listner: DocRequestAdapter.onItemClickListner):RecyclerView.ViewHolder(itemView) {
        val Alert_name: TextView = itemView.findViewById(R.id.patient_name13)


        init {
            itemView.setOnClickListener {
                listner.onClick(adapterPosition)

            }


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.doc_request_row,parent,false)
        return AlertViewHolder(view,mlistner)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {


        val Alert:String = AlertList?.get(position)
        holder.Alert_name.text=Alert





    }

    override fun getItemCount(): Int {
        return AlertList!!.size
    }

}