package com.example.emergency_alert_system.Dialogesandmaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.emergencyalertsystem.R

class map : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val webView = findViewById<WebView>(R.id.map)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.openstreetmap.org/#map=13/30.0444/31.2357")
    }
}