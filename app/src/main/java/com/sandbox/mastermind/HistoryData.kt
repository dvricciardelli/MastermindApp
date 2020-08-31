package com.sandbox.mastermind

data class HistoryData (val date: String, val result: Int, val lastGuess: String, val secret: String, val guesses: Int)