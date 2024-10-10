package com.olutoba.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.olutoba.utils.PermissionUtils
import com.olutoba.utils.showAlertDialog
import com.olutoba.utils.showMultiChoiceDialog
import com.olutoba.utils.showSingleChoiceDialog
import com.olutoba.xplore.R
import com.olutoba.xplore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private companion object {
        const val PERMISSION_REQUEST_CODE = 100
        const val EXAMPLE_URL = "https://www.google.com"
    }

    private lateinit var binding: ActivityMainBinding

    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result?.let {
                if (it.resultCode == Activity.RESULT_OK) {
                    val imageUri = it.data?.data
                    imageUri?.let {
                        binding.imageProfile.setImageURI(imageUri)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRequestPermission.setOnClickListener {
            requestPermissions()
        }

        binding.imageProfile.setOnClickListener {
            getImageFromGallery()
        }

        binding.buttonDialogOne.setOnClickListener {
            showAlertDialog(
                this,
                R.string.add_contact,
                R.string.add_contact_message,
                R.drawable.ic_add_contact,
                { showToast(getString(R.string.approve_add_contact)) },
                { showToast(getString(R.string.decline_add_contact)) }
            )
        }

        binding.buttonDialogTwo.setOnClickListener {
            showSingleChoiceDialog(
                this,
                R.string.select_options,
                arrayOf("Apple", "Google", "Netflix", "Meta", "Amazon"),
                { showToast(getString(R.string.accept_single_choice_dialog)) },
                { showToast(getString(R.string.declined_single_choice_dialog)) },
            )
        }

        binding.buttonDialogThree.setOnClickListener {
            showMultiChoiceDialog(
                this,
                R.string.select_options,
                arrayOf("Apple", "Google", "Netflix", "Meta"),
                { showToast(getString(R.string.accepted_multi_choice_dialog)) },
                { showToast(getString(R.string.decline_multi_choice_dialog)) },
            )
        }

        val countries = listOf("USA", "UK", "Canada", "Germany")
        val spinnerAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, countries)
        binding.autoTextCountries.setAdapter(spinnerAdapter)
        binding.autoTextCountries.setOnItemClickListener { parent, _, position, _ ->
            Toast.makeText(
                this@MainActivity,
                String.format(
                    this.getString(R.string.dialog_option_selected),
                    parent?.getItemAtPosition(position)
                ),
                Toast.LENGTH_SHORT
            ).show()
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
                        this, getString(R.string.permission_granted), Toast.LENGTH_SHORT
                    ).show()
                    Log.d("permissionReqGranted", permissions[i])
                }
            }
        }
    }

    private fun openUrlIntent() {
        val openWebUrlIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(EXAMPLE_URL)
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
            startActivity(Intent.createChooser(shareMsgIntent, getString(R.string.share_via)))
        }
    }

    private fun getImageFromGallery() {
        val imageIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        imageLauncher.launch(imageIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_contact -> showToast(getString(R.string.clicked_on_add_contact))
            R.id.menu_settings -> showToast(getString(R.string.clicked_on_settings))
            R.id.menu_favorites -> showToast(getString(R.string.clicked_on_favorites))
            R.id.menu_feedback -> showToast(getString(R.string.clicked_on_feedback))
            R.id.menu_close_app -> showToast(getString(R.string.clicked_on_close_app))
            else -> super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}