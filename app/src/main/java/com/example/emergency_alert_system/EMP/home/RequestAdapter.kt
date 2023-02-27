package com.example.emergency_alert_system.EMP.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.EMP.model.Alert
import com.example.emergencyalertsystem.R

class RequestAdapter(var AlertList: MutableList<Alert?>):RecyclerView.Adapter<RequestAdapter.AlertViewHolder>() {
    private lateinit var mlistner: onItemClickListner


    interface onItemClickListner{
        fun onClick(position: Int)


    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mlistner=listner
    }



    class AlertViewHolder(itemView: View,listner: RequestAdapter.onItemClickListner):RecyclerView.ViewHolder(itemView) {
        val Alert_name :TextView=itemView.findViewById(R.id.alert_name)
       val street_name :TextView=itemView.findViewById(R.id.street_name)
       val age :TextView=itemView.findViewById(R.id.alert_patient_age)

        init {
            itemView.setOnClickListener {
                listner.onClick(adapterPosition)

            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.request_row,parent,false)
        return AlertViewHolder(view,mlistner)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val Alert:Alert? =AlertList[position]
        holder.Alert_name.text=Alert!!.user_name
        holder.age.text=Alert!!.user_age.toString()
        holder.street_name.text=Alert!!.street_name

    }

    override fun getItemCount(): Int {
      return AlertList.size
    }

}