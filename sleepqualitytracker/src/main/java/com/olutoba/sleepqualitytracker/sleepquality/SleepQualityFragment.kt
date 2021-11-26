package com.olutoba.sleepqualitytracker.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.olutoba.sleepqualitytracker.R
import com.olutoba.sleepqualitytracker.database.SleepDatabase
import com.olutoba.sleepqualitytracker.databinding.FragmentSleepQualityBinding


/**
 * Fragment that displays a list of clickable icons, each representing a sleep quality rating.
 * Once the user taps an icon, the quality is set in the current sleepNight
 * and the database is updated.
 */
class SleepQualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepQualityBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_quality, container, false
        )

        val args = SleepQualityFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepQualityViewModelFactory(args.sleepNightKey, dataSource)
        val sleepQualityViewModel =
            ViewModelProvider(this, viewModelFactory)[SleepQualityViewModel::class.java]

        binding.sleepQualityViewModel = sleepQualityViewModel
        sleepQualityViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController()
                    .navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                sleepQualityViewModel.doneNavigating()
            }
        })

        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }
}
