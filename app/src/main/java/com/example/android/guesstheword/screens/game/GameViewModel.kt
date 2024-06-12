package com.example.android.guesstheword.screens.game


import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date
import kotlin.math.log


class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

   private val timer: CountDownTimer

   private val _countDown = MutableLiveData<String>()
    val countDown: LiveData<String>
        get() = _countDown


    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score


    // The current word
   private var _word = MutableLiveData<String>("")
    val word: LiveData<String>
        get() = _word

    private val _eventGameFinished = MutableLiveData<Boolean>()

    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished
    init {
        _eventGameFinished.value = false
        Log.i("GameViewModel","GameViewModel Created!")
        resetList()
        nextWord()
        timer = object : CountDownTimer(ONE_SECOND, COUNTDOWN_TIME){
            override fun onTick(p0: Long) {
                _countDown.value = DateUtils.formatElapsedTime(p0)
            }

            override fun onFinish() {
                _eventGameFinished.value = true
            }
        }
        timer.start()


        _score.value = 0
//        _word.value = ""

    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel","GameViewModel Destroyed!")
    }




    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    /** Methods for buttons presses **/

     fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    fun onGameFinished(){
        _eventGameFinished.value = false
    }
}