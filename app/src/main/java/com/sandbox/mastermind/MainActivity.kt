package com.sandbox.mastermind

import android.app.Dialog
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.mastermind_results.*

class MainActivity : AppCompatActivity() {

    private var secret = ""
    private var guessCount = 0
    private var isWinner: Boolean = false

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
        if(lastGuess.count() == CODE_LENGTH){
            tvHistory.text = lastGuess
        }
        tvInput.text = ""

    }

    fun onGuess(@Suppress("UNUSED_PARAMETER")view: View){

        val guess = tvInput.text.toString()

        if(guess.count() == CODE_LENGTH){
            guessCount++
            val evaluation = evaluateGuess(secret, guess)
            if (evaluation.isComplete()) {
                isWinner = true
                ResultDialogFunction()
            } else {
                //TODO: Add visibilty of how many guesses left
                if(guessCount >= MAX_GUESS){
                    ResultDialogFunction()
                } else {
                    tvFeedback.text ="Right postion: ${evaluation.rightPosition} Wrong position ${evaluation.wrongPosition}"
                    tvHistory.text=""
                }
            }

        }
        else{
            Toast.makeText(this, "Guess is at least ${CODE_LENGTH} characters", Toast.LENGTH_SHORT).show()
            tvInput.text = ""
        }

    }

    fun onFinish(@Suppress("UNUSED_PARAMETER")view: View){
        val btnState = btnFinish.text
        ResetGame()
    }

    private fun RestultActivity(){
        val intent = Intent(this, ResultsActivity::class.java)
        intent.putExtra(Constants.TOTAL_GUESSES, guessCount)
        intent.putExtra(Constants.SECRET, secret)

        startActivity(intent)
        finish()
    }

    private fun ResultDialogFunction(){
        val resultDialog = Dialog(this)

        resultDialog.setContentView(R.layout.mastermind_results)

        if(isWinner){
            resultDialog.tv_result.text = "You Won"
            resultDialog.tv_congratulations.text = "Congratulations!"
            resultDialog.iv_trophy.setImageResource(R.drawable.ic_trophy)
            resultDialog.tv_score.text = "You took $guessCount guesses to get secret $secret"
        } else {
            resultDialog.tv_result.text = "You Lost"
            resultDialog.tv_congratulations.text = "Keep Practicing"
            resultDialog.iv_trophy.setImageResource(R.drawable.ic_sad_emoji)
            resultDialog.tv_score.text = "You maxed out $guessCount guesses to get secret $secret"
        }


        resultDialog.btn_finish.setOnClickListener(View.OnClickListener {
            resultDialog.dismiss()
            ResetGame()

        })

        resultDialog.show()
    }

    private fun ResetGame(){
        secret = generateSecret()
        tvInput.text = ""
        tvHistory.text=""
        tvFeedback.text=""
        guessCount = 0

    }

    fun Evaluation.isComplete(): Boolean = rightPosition == CODE_LENGTH

}
