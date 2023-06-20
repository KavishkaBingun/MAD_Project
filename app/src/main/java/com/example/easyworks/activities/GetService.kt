package com.example.easyworks.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.easyworkscrud.R

class GetService : AppCompatActivity() {

    private val CALL_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_service)

        val callButton: ImageButton = findViewById(R.id.phonebtn)
        callButton.setOnClickListener { makeCall() }

        val emailButton: ImageButton = findViewById(R.id.emailbtn)
        emailButton.setOnClickListener { makeText() }
    }

    private fun makeCall() {
        val phoneNumber = "0704584332" // Replace with the desired phone number

        if (isCallPermissionGranted()) {
            val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            startActivity(dialIntent)
        } else {
            requestCallPermission()
        }
    }

    private fun makeText() {
        val phoneNumber = "0704584332" // Replace with the desired phone number

        val smsIntent = Intent(Intent.ACTION_SENDTO)
        smsIntent.data = Uri.parse("smsto:$phoneNumber")
        startActivity(smsIntent)
    }

    private fun isCallPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CALL_PHONE
            )
        ) {
            // Explain why the app needs the CALL_PHONE permission
            // You can show a dialog or a Snackbar with an explanation
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall()
                } else {
                    // Permission denied, handle accordingly
                    // You can show a dialog or a Snackbar indicating that the permission is required
                    // and provide an option to navigate to the app settings screen.
                    val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    settingsIntent.data = uri
                    startActivity(settingsIntent)
                }
                return
            }
        }
    }
}
