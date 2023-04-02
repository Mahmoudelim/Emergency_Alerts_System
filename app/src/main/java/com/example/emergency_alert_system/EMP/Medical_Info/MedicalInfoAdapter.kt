package com.example.emergency_alert_system.EMP.Medical_Info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergencyalertsystem.R

class ChronicListAdapter( var chronicList: List<String>)
    : RecyclerView.Adapter<ChronicListAdapter.ChronicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChronicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chronic_item, parent, false)
        return ChronicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChronicViewHolder, position: Int) {
        holder.bind(chronicList[position])
    }

    override fun getItemCount(): Int {
        return chronicList.size
    }

    inner class ChronicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chronicTextView: TextView = itemView.findViewById(R.id.chronics)

        fun bind(chronic: String) {
            chronicTextView.text = chronic
        }
    }
}
