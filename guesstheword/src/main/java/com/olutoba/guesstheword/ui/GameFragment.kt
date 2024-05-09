package com.olutoba.guesstheword.ui

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.olutoba.guesstheword.R
import com.olutoba.guesstheword.databinding.GameFragmentBinding
import com.olutoba.guesstheword.viewmodel.BuzzType
import com.olutoba.guesstheword.viewmodel.GameViewModel

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventGameFinish.observe(viewLifecycleOwner) {
            if (it) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        }

        viewModel.eventBuzz.observe(viewLifecycleOwner) {
            if (it != BuzzType.NO_BUZZ) {
                buzz(it.pattern)
                viewModel.onBuzzComplete()
            }
        }

        return binding.root

    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            }
        }
    }

    private fun gameFinished() {
        val currentScore = viewModel.score.value ?: 0
        findNavController().navigate(GameFragmentDirections.actionGameToScore(currentScore))
    }

}