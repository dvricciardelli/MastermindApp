package com.sandbox.mastermind

import com.sandbox.mastermind.Evaluation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var secret = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        secret = generateSecret()
    }

    fun onLetter(view: View){
        tvInput.append((view as Button).text)
    }

    fun onClear(@Suppress("UNUSED_PARAMETER")view: View){
        tvInput.text = ""
    }

    fun onGuess(@Suppress("UNUSED_PARAMETER")view: View){
        val guess = tvInput.text.toString()

        if(guess.count() == CODE_LENGTH){
            val evaluation = evaluateGuess(secret, guess)
            if (evaluation.isComplete()) {
                Toast.makeText(this, "The guess is correct", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Right postion: ${evaluation.rightPosition} wrong position ${evaluation.wrongPosition}", Toast.LENGTH_SHORT).show()

            }
        }
        else{
            Toast.makeText(this, "Guess is at least ${CODE_LENGTH} characters", Toast.LENGTH_SHORT).show()
            tvInput.text = ""
        }


    }

    fun Evaluation.isComplete(): Boolean = rightPosition == CODE_LENGTH
}
