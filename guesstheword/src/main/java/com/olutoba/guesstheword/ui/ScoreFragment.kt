package com.olutoba.guesstheword.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.olutoba.guesstheword.R
import com.olutoba.guesstheword.databinding.ScoreFragmentBinding
import com.olutoba.guesstheword.viewmodel.ScoreViewModel
import com.olutoba.guesstheword.viewmodel.ScoreViewModelFactory

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {
    private lateinit var viewModelFactory: ScoreViewModelFactory
    private lateinit var viewModel: ScoreViewModel
    private val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
    private lateinit var binding: ScoreFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)

        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProvider(this, viewModelFactory)[ScoreViewModel::class.java]
        binding.scoreViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventPlayAgain.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        }

        return binding.root
    }
}
