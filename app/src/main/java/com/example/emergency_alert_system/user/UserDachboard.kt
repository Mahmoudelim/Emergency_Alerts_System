package com.example.emergency_alert_system.user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewConfiguration
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.emergencyalertsystem.R
import kotlinx.android.synthetic.main.activity_user_dachboard.*
import kotlinx.android.synthetic.main.activity_user_dachboard.view.*

class UserDachboard : AppCompatActivity() {
    lateinit var appBarConfiguration: AppBarConfiguration
     lateinit var navController: NavController
     lateinit var drawerLayout: DrawerLayout
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dachboard)
        navController=findNavController(R.id.user_nav_host_fragment)
        drawerLayout=findViewById(R.id.drawer_layout)
        nav_view.setupWithNavController(navController)
        appBarConfiguration= AppBarConfiguration(navController.graph,drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.user_nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()
    }
}