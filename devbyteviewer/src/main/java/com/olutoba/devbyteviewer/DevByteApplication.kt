package com.olutoba.devbyteviewer

import android.app.Application
import timber.log.Timber

class DevByteApplication : Application() {

    /* This is called before the first screen is shown to the user.
    * Use it to setup any background tasks, running expensive setup operations in a
    * background thread to avoid delaying app start. */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}