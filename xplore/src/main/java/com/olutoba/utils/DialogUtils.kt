package com.olutoba.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.olutoba.xplore.R


/**
 * Created by Onikoyi Damola Olutoba
 * DATE: 07 October, 2024
 * EMAIL: damexxey94@gmail.com
 */

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

fun showAlertDialog(
    context: Context,
    @StringRes title: Int,
    @StringRes message: Int,
    @DrawableRes icon: Int,
    positiveAction: () -> Unit,
    negativeAction: () -> Unit
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setIcon(icon)
        .setPositiveButton(context.getString(R.string.proceed)) { dialog, _ ->
            positiveAction()
            dialog.dismiss()
        }
        .setNegativeButton(context.getString(R.string.decline)) { dialog, _ ->
            negativeAction()
            dialog.dismiss()
        }
        .show()
}

fun showSingleChoiceDialog(
    context: Context,
    @StringRes title: Int,
    options: Array<String>,
    positiveAction: () -> Unit,
    negativeAction: () -> Unit
) {

    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setSingleChoiceItems(options, 0) { _, isChecked ->
            Toast.makeText(
                context,
                "You have selected ${options[isChecked]}",
                Toast.LENGTH_SHORT
            ).show()
        }
        .setPositiveButton(context.getString(R.string.proceed)) { dialog, _ ->
            positiveAction()
            dialog.dismiss()
        }
        .setNegativeButton(context.getString(R.string.decline)) { dialog, _ ->
            negativeAction()
            dialog.dismiss()
        }
        .show()
}

fun showMultiChoiceDialog(
    context: Context,
    @StringRes title: Int,
    options: Array<String>,
    positiveAction: () -> Unit,
    negativeAction: () -> Unit
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMultiChoiceItems(
            options,
            booleanArrayOf(false, false, false, false)
        ) { _, position, isChecked ->
            if (isChecked) {
                showToast(
                    context,
                    String.format(
                        context.getString(R.string.dialog_option_selected),
                        options[position]
                    )
                )
            } else {
                showToast(
                    context,
                    String.format(
                        context.getString(R.string.dialog_option_selected),
                        options[position]
                    )
                )
            }
        }
        .setPositiveButton(context.getString(R.string.proceed)) { dialog, _ ->
            positiveAction()
            dialog.dismiss()
        }
        .setNegativeButton(context.getString(R.string.decline)) { dialog, _ ->
            negativeAction()
            dialog.dismiss()
        }
        .show()
}