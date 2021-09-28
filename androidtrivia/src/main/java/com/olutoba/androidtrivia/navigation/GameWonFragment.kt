package com.olutoba.androidtrivia.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.olutoba.androidtrivia.R
import com.olutoba.androidtrivia.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false
        )
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        val args = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        Toast.makeText(
            context,
            "NumCorrect: ${args?.numCorrect}, NumQuestions: ${args?.numQuestions}",
            Toast.LENGTH_SHORT
        ).show()
        return binding.root
    }

}
