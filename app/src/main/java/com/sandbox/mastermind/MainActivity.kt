package com.sandbox.mastermind

import android.app.Dialog
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.mastermind_results.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        val tvInstruction = textView2.append(" " + gamePlay.GetLastChar().toString())
        Log.i("Secret", gamePlay.GetSecret())
        gridView()
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

    fun onHistory(@Suppress("UNUSED_PARAMETER")view: View){
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    fun onGuess(@Suppress("UNUSED_PARAMETER")view: View){

        val guess = tvInput.text.toString()

        if(gamePlay.isGuessLength(guess)){
            //guessCount++
            gamePlay.evaluateGuess(guess)
            if (gamePlay.isWinner() === 1) {
                ResultDialogFunction()
            } else {
                //TODO: Add visibilty of how many guesses left
                if(gamePlay.isLoser()){
                    ResultDialogFunction()
                } else {
                    tvFeedback.text ="Right postion: ${gamePlay.GetRightAnswers()} Wrong position ${gamePlay.GetWrongAnswers()}"
                    tvHistory.text=""
                }
            }

        }
        else{
            Toast.makeText(this, "Guess is at least ${gamePlay.SecretSize()} characters", Toast.LENGTH_SHORT).show()
            tvInput.text = ""
        }

    }

    fun onFinish(@Suppress("UNUSED_PARAMETER")view: View){

        ResetGame()
    }

    private fun gridView(){
        rvGuessList.layoutManager = GridLayoutManager(this, 3)
        val itemAdapter = ItemAdapter(this, getGuessList())
        rvGuessList.adapter = itemAdapter
    }

    private fun ResultDialogFunction(){
        val resultDialog = Dialog(this)

        resultDialog.setContentView(R.layout.mastermind_results)

        if(gamePlay.isWinner() === 1){
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
        addDateToDatabase()
        resultDialog.show()
    }

    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.i("DATE:", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this, null )
        val game = HistoryData(date, gamePlay.GetResult(), gamePlay.GetGuess(), gamePlay.GetSecret(), gamePlay.GetGuessCount() )
        dbHandler.addGame(date, game)
        Log.i("DATE: ", "Added")

    }

    private fun ResetGame(){
        gamePlay = Game.setSecret()
        tvInput.text = ""
        tvHistory.text=""
        tvFeedback.text=""
        Log.i("Secret", gamePlay.GetSecret())

    }

    private fun getGuessList(): ArrayList<String>{
        val list = ArrayList<String>()

        for(i in 'A' .. gamePlay.GetLastChar()){
            list.add(i.toString())
        }

        return list
    }

}
