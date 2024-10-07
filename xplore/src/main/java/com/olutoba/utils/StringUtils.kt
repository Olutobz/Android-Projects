package com.olutoba.utils

import android.content.Context
import android.widget.Toast


/**
 * Created by Onikoyi Damola Olutoba
 * DATE: 07 October, 2024
 * EMAIL: damexxey94@gmail.com
 */

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}