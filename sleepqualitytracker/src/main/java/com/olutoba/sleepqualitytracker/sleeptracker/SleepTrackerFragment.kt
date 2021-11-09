package com.olutoba.sleepqualitytracker.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.olutoba.sleepqualitytracker.R
import com.olutoba.sleepqualitytracker.databinding.FragmentSleepTrackerBinding


/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database.
 */
class SleepTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_tracker, container, false
        )

        return binding.root
    }
}
