package com.sandbox.mastermind

class Game {
    companion object{
        fun setSecret(): GameModel{
            return GameModel(2,4,'A', 'I')

        }
    }
}