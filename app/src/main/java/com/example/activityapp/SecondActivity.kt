package com.example.activityapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second) // Ensure the correct layout is set

        // Find the button by its ID
        val buttonMainActivity = findViewById<MaterialButton>(R.id.buttonMainActivity)

        // Set click listener for the Main Activity button
        buttonMainActivity.setOnClickListener {
            finish() // Close the current activity and return to MainActivity
        }
    }
}