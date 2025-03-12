package com.example.activityapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import android.widget.ImageView
import android.widget.Toast

class ImageActivity : AppCompatActivity() {

    private lateinit var imageViewCaptured: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1

    // Register the permission request launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted, open the camera
            dispatchTakePictureIntent()
        } else {
            // Permission denied, show a message to the user
            Toast.makeText(this, "Camera permission is required to capture images", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        // Initialize views
        val buttonCaptureImage = findViewById<MaterialButton>(R.id.buttonCaptureImage)
        imageViewCaptured = findViewById(R.id.imageViewCaptured)

        // Set click listener for the Capture Image button
        buttonCaptureImage.setOnClickListener {
            checkCameraPermission()
        }
    }

    // Check if the camera permission is granted
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted, open the camera
            dispatchTakePictureIntent()
        } else {
            // Request the camera permission
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Open the camera to capture an image
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // Handle the result of the captured image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageViewCaptured.setImageBitmap(imageBitmap)
        }
    }
}