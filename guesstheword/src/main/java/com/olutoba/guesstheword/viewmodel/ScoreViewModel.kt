package com.olutoba.guesstheword.viewmodel

import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(finalScore: Int) : ViewModel() {

    init {
        Timber.i("Final score is $finalScore")
    }

}