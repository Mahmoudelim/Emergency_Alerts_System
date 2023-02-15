package com.example.emergency_alert_system.user.AssignDoctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.Creation.Doctor
import com.example.emergencyalertsystem.R
import kotlinx.android.synthetic.main.search_row.view.*

class searchAdapter(var searcList: List<Doctor>) : RecyclerView.Adapter<searchAdapter.searchViewHoldr>() {

    class searchViewHoldr (itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(Doc:Doctor){
            itemView.doctor_name.text=Doc.Name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchViewHoldr {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.search_row,parent,false)
        return searchViewHoldr(view)
    }

    override fun onBindViewHolder(holder: searchViewHoldr, position: Int) {
        holder.bind(searcList[position])

    }

    override fun getItemCount(): Int {
        return searcList.size
    }



    }
