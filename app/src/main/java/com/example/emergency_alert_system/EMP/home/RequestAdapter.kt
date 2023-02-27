package com.example.emergency_alert_system.EMP.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.EMP.model.Alerts
import com.example.emergencyalertsystem.R

class RequestAdapter(var AlertList: ArrayList<Alerts>):RecyclerView.Adapter<RequestAdapter.AlertViewHolder>() {

    class AlertViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val Alert_name :TextView=itemView.findViewById(R.id.alert_name)
       val street_name :TextView=itemView.findViewById(R.id.street_name)
       val age :TextView=itemView.findViewById(R.id.alert_patient_age)
        val acceptBtn:Button=itemView.findViewById(R.id.acceptButton)
        val forwardBtn:Button=itemView.findViewById(R.id.ForwardButton)






    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.request_row,parent,false)
        return AlertViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {


        val Alert:Alerts? = AlertList?.get(position)
        holder.Alert_name.text=Alert!!.user_name
        holder.age.text=Alert!!.user_age.toString()
        holder.street_name.text=Alert!!.street_name
        holder.acceptBtn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val bundle=Bundle()
                bundle.putString("userName",holder.Alert_name.text.toString())
               v!!.findNavController().navigate(R.id.action_home4_to_requestAcceptance,bundle)


            }
        })
     holder.forwardBtn.setOnClickListener(object :View.OnClickListener{
         override fun onClick(v: View?) {

             v!!.findNavController().navigate(R.id.action_home4_to_forwardRequest)


         }
     })

    }

    override fun getItemCount(): Int {
      return AlertList!!.size
    }

}