package com.sandbox.mastermind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.mastermind_pass.*

class PassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mastermind_pass)

        val guesses = intent.getIntExtra(Constants.TOTAL_GUESSES, 0)
        val secret = intent.getStringExtra(Constants.SECRET)

        tv_score.text = "You took $guesses guesses to get $secret"

        btn_finish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}