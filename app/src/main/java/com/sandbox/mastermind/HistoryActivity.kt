package com.sandbox.mastermind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tvHistory

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        getAllCompletedGames()

    }

    private fun getAllCompletedGames(){
        val dbHandler = SqliteOpenHelper(this, null)
        val allCompletedGamesList = dbHandler.getAllCompletedGamesList()

        if(allCompletedGamesList.size > 0){
            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE
            tvNoDataAvailable.visibility = View.GONE

            rvHistory.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this, allCompletedGamesList)
            rvHistory.adapter = historyAdapter
        } else {
            tvHistory.visibility = View.GONE
            rvHistory.visibility = View.GONE
            tvNoDataAvailable.visibility = View.VISIBLE
        }
    }
}