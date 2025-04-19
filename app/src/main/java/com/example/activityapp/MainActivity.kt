package com.example.activityapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION_CODE = 1001
    private val customPermission = "com.example.activityapp.MSE412"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Request custom permission
        if (ContextCompat.checkSelfPermission(this, customPermission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(customPermission),
                REQUEST_PERMISSION_CODE
            )
        }

        findViewById<MaterialButton>(R.id.buttonExplicit).setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, customPermission) == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(this, SecondActivity::class.java))
            } else {
                Toast.makeText(this, "Custom permission not granted!", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<MaterialButton>(R.id.buttonImplicit).setOnClickListener {
            val intent = Intent("com.example.activityapp.OPEN_SECOND_ACTIVITY")
            startActivity(intent)
        }

        findViewById<MaterialButton>(R.id.buttonViewImage).setOnClickListener {
            startActivity(Intent(this, ImageActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
