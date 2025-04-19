package com.example.activityapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Toast.makeText(this, "SecondActivity Launched", Toast.LENGTH_SHORT).show()

        val challenges = findViewById<TextView>(R.id.textChallenges)
        challenges.text = """
            1. Fragmentation
            2. Battery efficiency
            3. Security
            4. Performance
            5. UX consistency
        """.trimIndent()

        findViewById<MaterialButton>(R.id.buttonMainActivity).setOnClickListener {
            finish()
        }
    }
}
