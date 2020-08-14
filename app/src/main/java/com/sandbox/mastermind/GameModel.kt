package com.sandbox.mastermind

import kotlin.random.Random

class GameModel (private val MAX_GUESS: Int,
                 private val CODE_LENGTH: Int,
                 private val firstChar: Char,
                 private val lastChar: Char

){

    private val ALPHABET = firstChar..lastChar
    private val secretString = createSecret()
    private var isWinner = false
    private var guessCount = 0

    private fun createSecret(): String{

        guessCount = 0
        val chars = ALPHABET.toMutableList()

        return buildString {
            for (i in 1..CODE_LENGTH) {
                val letter = chars[Random.nextInt(chars.size)]
                append(letter)

            }
        }
    }

    fun evaluateGuess(guessString: String): Evaluation{

        guessCount++
        val wrong = mutableMapOf<Int, Char>()
        val right = mutableMapOf<Int, Char>()
        val wrongPending = mutableMapOf<Int, Char>()

        for (guess in guessString.withIndex()){

            for (secret in secretString.withIndex()){
                if(guess == secret){

                    if(!wrongPending.isEmpty()){
                        right.put(secret.index, secret.value)
                        wrongPending.clear()
                        break
                    }
                    if(wrong[secret.index] == guess.value){
                        right.put(secret.index, secret.value)
                        wrong.remove(guess.index)
                    } else {
                        right.put(secret.index, secret.value)
                        break
                    }

                } else if (guess.value == secret.value){

                    if(secret.value != right[secret.index]){
                        if(wrongPending.isEmpty() && secret.value != wrong[secret.index]){
                            wrongPending.put(secret.index, secret.value)

                        }
                    }
                }
            }

            if (!wrongPending.isEmpty()){
                wrong.putAll(wrongPending)
                wrongPending.clear()

            }
        }
        val result = Evaluation(right.count(),wrong.count())
        return result
    }

    fun GetGuessCount(): Int{
        return guessCount
    }

    fun SetWinner() {
        isWinner = true
    }

    fun GetWinner(): Boolean{
        return isWinner
    }

    fun isLoser(): Boolean{
        return MAX_GUESS <= guessCount
    }

    fun isGuessLength(guess: String): Boolean {
        return guess.count()==CODE_LENGTH
    }

    fun GetSecret(): String{
        return secretString
    }
}