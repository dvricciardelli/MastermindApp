package com.sandbox.mastermind

import kotlin.random.Random

val ALPHABET = 'A'..'F'
const val CODE_LENGTH = 4

fun generateSecret(): String {
    val chars = ALPHABET.toMutableList()
    return buildString {
        for (i in 1..CODE_LENGTH) {
            val letter = chars[Random.nextInt(chars.size)]
            append(letter)

        }
    }
}