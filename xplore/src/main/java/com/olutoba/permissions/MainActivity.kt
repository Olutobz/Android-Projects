package com.olutoba.permissions

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
            if (!PermissionUtil.hasCameraPermission(this@MainActivity)) {
                add(android.Manifest.permission.CAMERA)
            }

            if (!PermissionUtil.hasWriteToExternalStoragePermission(this@MainActivity)) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            if (!PermissionUtil.hasForegroundLocationPermission(this@MainActivity)) {
                add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            if (!PermissionUtil.hasBackgroundLocationPermission(this@MainActivity)) {
                add(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
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

    private companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }

}