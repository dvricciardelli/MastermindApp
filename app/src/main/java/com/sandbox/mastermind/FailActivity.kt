package com.sandbox.mastermind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.mastermind_fail.*

class FailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mastermind_fail)

        val guesses = intent.getIntExtra(Constants.TOTAL_GUESSES, 0)
        val secret = intent.getStringExtra(Constants.SECRET)

        tv_score.text = "You reached guesses allowed $guesses.  The secret was $secret"

        btn_finish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}