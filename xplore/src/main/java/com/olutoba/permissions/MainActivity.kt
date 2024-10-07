package com.olutoba.permissions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.olutoba.xplore.R
import com.olutoba.xplore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val getImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result?.let {
                if (it.resultCode == Activity.RESULT_OK) {
                    val imageUri = it.data?.data
                    imageUri?.let {
                        binding.ivIcon.setImageURI(imageUri)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequestPermission.setOnClickListener {
            requestPermissions()
        }

        binding.ivIcon.setOnClickListener {
            getImageFromGallery()
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

    private fun openUrlIntent() {
        val openWebUrlIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("https://www.google.com")
        }
        if (openWebUrlIntent.resolveActivity(packageManager) != null) {
            startActivity(openWebUrlIntent)
        }
    }

    private fun shareTextIntent() {
        val shareMsgIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hello World")
            type = "text/plain"
        }
        if (shareMsgIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(shareMsgIntent, "Share Via"))
        }
    }

    private fun getImageFromGallery() {
        val imageIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        getImageLauncher.launch(imageIntent)
    }

    private companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_contact -> Toast.makeText(
                this,
                "clicked on add contact",
                Toast.LENGTH_SHORT
            ).show()

            R.id.menu_settings -> Toast.makeText(
                this,
                "clicked on settings",
                Toast.LENGTH_SHORT
            ).show()

            R.id.menu_favorites -> Toast.makeText(
                this,
                "clicked on favorites",
                Toast.LENGTH_SHORT
            ).show()

            R.id.menu_feedback -> Toast.makeText(
                this,
                "clicked on feedback",
                Toast.LENGTH_SHORT
            ).show()

            R.id.menu_close_app -> Toast.makeText(
                this,
                "clicked on close app",
                Toast.LENGTH_SHORT
            ).show()

            else -> super.onOptionsItemSelected(item)
        }

        return true
    }

}