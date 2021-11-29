package com.olutoba.sleepqualitytracker

import android.app.Application
import timber.log.Timber

class SleepApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}