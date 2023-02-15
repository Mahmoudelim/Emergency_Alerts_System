package com.example.emergency_alert_system.user.medicines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency_alert_system.user.creation.user_midical_info
import com.example.emergency_alert_system.user.model.medicine
import com.example.emergencyalertsystem.R
import kotlinx.android.synthetic.main.medicine_card.view.*
import kotlinx.android.synthetic.main.search_row.view.*

class medicineAdapter(var medicineList: List<medicine>):
    RecyclerView.Adapter<medicineAdapter.medicineViewHolder>() {
    class medicineViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

         val medicineName : TextView=itemView.findViewById(R.id.med_name)
         val medicineTime : TextView=itemView.findViewById(R.id.med_time)
         val attachedWith : TextView=itemView.findViewById(R.id.med_doc)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): medicineViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.medicine_card,parent,false)
        return medicineAdapter.medicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: medicineViewHolder, position: Int) {
    val medicine:medicine=medicineList[position]
        holder.medicineName.text=medicine.medicine_name
        holder.medicineTime.text=medicine.medicine_time.toString()
        holder.attachedWith.text=medicine.assignedWith

    }

    override fun getItemCount(): Int {
    return medicineList.size
    }
}