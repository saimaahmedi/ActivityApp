package com.example.activityapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find buttons by ID
        val buttonExplicit: MaterialButton = findViewById(R.id.buttonExplicit)
        val buttonImplicit: MaterialButton = findViewById(R.id.buttonImplicit)
        val buttonViewImage: MaterialButton = findViewById(R.id.buttonViewImage)

        // Set click listeners
        buttonExplicit.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        buttonImplicit.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
            startActivity(intent)
        }

        buttonViewImage.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }
    }
}