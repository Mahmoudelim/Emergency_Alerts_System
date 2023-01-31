package com.example.emergency_alert_system.user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.example.emergencyalertsystem.R
import com.google.android.material.navigation.NavigationView

class UserDachboard : AppCompatActivity() {
    lateinit var toogle :ActionBarDrawerToggle

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dachboard)
        val drawerLayout :DrawerLayout=findViewById(R.id.drawer_layout)
        val navView :NavigationView=findViewById(R.id.nav_view)
        toogle= ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
             //   R.id.nav_home->this.findNavController(R.id.)
                R.id.nav_medicine->this.findNavController(R.id.action_home3_to_medicine)
                R.id.nav_Alerts->this.findNavController(R.id.action_home3_to_alerts)
                R.id.nav_edit->this.findNavController(R.id.action_home3_to_editInfo)
            }
            true
        }
    }
}