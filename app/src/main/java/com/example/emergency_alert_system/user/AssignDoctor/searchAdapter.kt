package com.example.emergency_alert_system.user.AssignDoctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.Doctor.Creation.Doctor
import com.example.emergencyalertsystem.R
import kotlinx.android.synthetic.main.search_row.view.*

class searchAdapter(var searcList: List<Doctor>) : RecyclerView.Adapter<searchAdapter.searchViewHoldr>() {

    private lateinit var mlistner:onItemClickListner

    interface onItemClickListner{
        fun onClick(position: Int)


    }
    fun setOnItemClickListner(listner: onItemClickListner){
      mlistner=listner
    }

    class searchViewHoldr (itemView: View,listner: onItemClickListner):RecyclerView.ViewHolder(itemView){

        fun bind(Doc:Doctor){
            itemView.doctor_name.text=Doc.Name
            itemView.doctor_specialization.text=Doc.spicialization
        }
        init {
            itemView.setOnClickListener{
                listner.onClick(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchViewHoldr {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.search_row,parent,false)
        return searchViewHoldr(view,mlistner)
    }

    override fun onBindViewHolder(holder: searchViewHoldr, position: Int) {
        holder.bind(searcList[position])

    }

    override fun getItemCount(): Int {
        return searcList.size
    }



    }
