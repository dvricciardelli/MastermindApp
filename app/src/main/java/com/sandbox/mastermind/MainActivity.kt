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

    //private var secret = ""
    //private var guessCount = 0
    //private var isWinner: Boolean = false
    private lateinit var gamePlay: GameModel

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Add rotating capability
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //secret = generateSecret()
        gamePlay = Game.setSecret()
    }

    fun onLetter(view: View){
        tvInput.append((view as Button).text)
    }

    fun onClear(@Suppress("UNUSED_PARAMETER")view: View){
        //TODO: Consider when user cleared guess
        val guess = tvInput.text.toString()
        if(gamePlay.isGuessLength(guess)){
            tvHistory.text = guess
        }
        tvInput.text = ""

    }

    fun onGuess(@Suppress("UNUSED_PARAMETER")view: View){

        val guess = tvInput.text.toString()

        if(gamePlay.isGuessLength(guess)){
            //guessCount++
            val evaluation = gamePlay.evaluateGuess(guess)
            if (evaluation.isWinner()) {
                gamePlay.SetWinner()
                ResultDialogFunction()
            } else {
                //TODO: Add visibilty of how many guesses left
                if(gamePlay.isLoser()){
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

        ResetGame()
    }

    private fun ResultDialogFunction(){
        val resultDialog = Dialog(this)

        resultDialog.setContentView(R.layout.mastermind_results)

        if(gamePlay.GetWinner()){
            resultDialog.tv_result.text = "You Won"
            resultDialog.tv_congratulations.text = "Congratulations!"
            resultDialog.iv_trophy.setImageResource(R.drawable.ic_trophy)
            resultDialog.tv_score.text = "You took ${gamePlay.GetGuessCount()} guesses to get secret ${gamePlay.GetSecret()}"
        } else {
            resultDialog.tv_result.text = "You Lost"
            resultDialog.tv_congratulations.text = "Keep Practicing"
            resultDialog.iv_trophy.setImageResource(R.drawable.ic_sad_emoji)
            resultDialog.tv_score.text = "You maxed out ${gamePlay.GetGuessCount()} guesses to get secret ${gamePlay.GetSecret()}"
        }


        resultDialog.btn_finish.setOnClickListener(View.OnClickListener {
            resultDialog.dismiss()
            ResetGame()

        })

        resultDialog.show()
    }

    private fun ResetGame(){
        gamePlay = Game.setSecret()
        tvInput.text = ""
        tvHistory.text=""
        tvFeedback.text=""

    }

    fun Evaluation.isWinner(): Boolean = rightPosition == CODE_LENGTH

}
