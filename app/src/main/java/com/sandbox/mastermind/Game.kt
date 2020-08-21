package com.sandbox.mastermind

class Game {
    companion object{
        fun setSecret(): GameModel{
            return GameModel(10,4,'A', 'F')

        }
    }
}