package com.sandbox.mastermind

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var secret = ""
    private var guessCount = 0
    private var isWinner = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Add rotating capability
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        secret = generateSecret()
    }

    fun onLetter(view: View){
        tvInput.append((view as Button).text)
    }

    fun onClear(@Suppress("UNUSED_PARAMETER")view: View){
        //TODO: Consider when user cleared guess
        val lastGuess = tvInput.text
        tvInput.text = ""
        tvHistory.text = lastGuess

    }

    fun onGuess(@Suppress("UNUSED_PARAMETER")view: View){

        val guess = tvInput.text.toString()

        if(guess.count() == CODE_LENGTH){
            val evaluation = evaluateGuess(secret, guess)
            if (evaluation.isComplete()) {
                tvFeedback.text = "The guess is correct"
                btnFinish.text = "Results"
                btnClear.isEnabled = false
                btnGuess.isEnabled = false
                isWinner = true
            } else {
                //TODO: Add visibilty of how many guesses left
                tvFeedback.text ="Right postion: ${evaluation.rightPosition} Wrong position ${evaluation.wrongPosition}"
                guessCount++
            }
            if(guessCount >= MAX_GUESS){
                btnFinish.text = "Results"
                btnClear.isEnabled = false
                btnGuess.isEnabled = false
            }
            tvHistory.text=""
        }
        else{
            Toast.makeText(this, "Guess is at least ${CODE_LENGTH} characters", Toast.LENGTH_SHORT).show()
            tvInput.text = ""
        }

    }

    fun onFinish(@Suppress("UNUSED_PARAMETER")view: View){
        val btnState = btnFinish.text

        //TODO: add constants for the state
        if(btnState == "RESTART") {
            secret = generateSecret()
            tvInput.text = ""
            tvHistory.text=""
            tvFeedback.text=""
            guessCount = 0
        }
        else {
            val intent = Intent(this, ResultsActivity::class.java)
            intent.putExtra(Constants.TOTAL_GUESSES, guessCount)
            intent.putExtra(Constants.SECRET, secret)
            startActivity(intent)
            finish()
        }

    }

    fun Evaluation.isComplete(): Boolean = rightPosition == CODE_LENGTH

}
