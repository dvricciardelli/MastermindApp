package com.sandbox.mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secretString: String, guessString: String): Evaluation {
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
