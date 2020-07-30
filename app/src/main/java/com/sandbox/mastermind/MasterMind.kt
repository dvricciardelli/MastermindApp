package com.sandbox.mastermind

import kotlin.random.Random

class MasterMind {

    private val ALPHABET = 'A'..'F'
    private val CODE_LENGTH = 4

    lateinit var secretString: String

    init{
        secretString = generateSecret()
    }

    fun generateSecret(): String {
        val chars = ALPHABET.toMutableList()
        return buildString {
            for (i in 1..CODE_LENGTH) {
                val letter = chars[Random.nextInt(chars.size)]
                append(letter)
            }
        }
    }

    fun evaluateGuess(guessString: String): Evaluation {
        val wrong = mutableMapOf<Int, Char>()
        val right = mutableMapOf<Int, Char>()
        val wrongPending = mutableMapOf<Int, Char>()

        for (guess in guessString.withIndex()){
            for (secret in this.secretString.withIndex()){
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

        return Evaluation(right.count(),wrong.count())
    }
}