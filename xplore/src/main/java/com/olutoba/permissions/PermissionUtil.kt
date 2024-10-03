package com.olutoba.permissions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.olutoba.xplore.R

/**
 * Created by Onikoyi Damola Olutoba
 * DATE: 30 May, 2024
 * EMAIL: damexxey94@gmail.com
 */

object PermissionUtil {

    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun showRationaleDialog(
        context: Context,
        @StringRes title: Int,
        @StringRes rationale: Int,
        positiveAction: () -> Unit,
        negativeAction: () -> Unit,
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.resources.getString(title))
            .setMessage(context.resources.getString(rationale))
            .setPositiveButton(context.getString(R.string.proceed)) { _, _ ->
                positiveAction()
            }
            .setNegativeButton(context.getString(R.string.decline)) { _, _ ->
                negativeAction()
            }
            .show()
    }

    fun openPermissionSettings(context: Context, onError: () -> Unit) {
        try {
            val intent = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", context.packageName, null)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            onError()
        }
    }

}