package com.olutoba.permissions

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.olutoba.xplore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequestPermission.setOnClickListener {
            requestPermissions()
        }

    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>().apply {
            if (!PermissionUtils.hasCameraPermission(this@MainActivity)) {
                add(android.Manifest.permission.CAMERA)
            }

            if (!PermissionUtils.hasWriteToExternalStoragePermission(this@MainActivity)) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            if (!PermissionUtils.hasForegroundLocationPermission(this@MainActivity)) {
                add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            if (!PermissionUtils.hasBackgroundLocationPermission(this@MainActivity)) {
                add(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            PermissionUtils.requestPermission(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this, "Permission Granted", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("permissionReqGranted", permissions[i])
                }
            }
        }
    }

    fun openUrlIntent() {
        val openUrlIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("https://www.google.com")
        }
        if (openUrlIntent.resolveActivity(packageManager) != null) {
            startActivity(openUrlIntent)
        }
    }

    fun sendMessageIntent() {
        val shareMsgIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hello World")
            type = "text/plain"
        }
        if (shareMsgIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(shareMsgIntent, "Share Via"))
        }
    }

    fun getAppContentIntent() {
        val getContentsIntent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        if (getContentsIntent.resolveActivity(packageManager) != null) {
            startActivity(getContentsIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }

}