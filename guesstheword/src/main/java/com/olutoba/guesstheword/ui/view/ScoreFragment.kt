package com.olutoba.guesstheword.ui.view

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)

        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)

        viewModel.score.observe(viewLifecycleOwner, { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() }

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }
}
