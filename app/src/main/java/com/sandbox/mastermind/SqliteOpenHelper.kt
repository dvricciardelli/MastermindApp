package com.sandbox.mastermind

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 3
        private val DATABASE_NAME = "MasterMindGame.db"
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completed_date"
        private val COLUMN_RESULT = "result"
        private val COLUMN_GUESSES = "guesses"
        private val COLUMN_SECRET = "secret"
        private val COLUMN_LAST_GUESS = "last_guess"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_GAME_TABLE = ("CREATE TABLE "
                + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_COMPLETED_DATE + " TEXT,"
                + COLUMN_RESULT + " INTEGER,"
                + COLUMN_GUESSES + " INTEGER,"
                + COLUMN_SECRET + " TEXT,"
                + COLUMN_LAST_GUESS + " TEXT)")
        db?.execSQL(CREATE_GAME_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(db)
    }

    fun addGame(date: String, game: HistoryData){
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE, date)
        values.put(COLUMN_RESULT, game.result)
        values.put(COLUMN_GUESSES, game.guesses)
        values.put(COLUMN_SECRET, game.secret)
        values.put(COLUMN_LAST_GUESS, game.lastGuess)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }

    fun getAllCompletedGamesList(): ArrayList<HistoryData>{
        val games = ArrayList<HistoryData>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)


        while(cursor.moveToNext()){
            val game = HistoryData(cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
                ,cursor.getInt(cursor.getColumnIndex(COLUMN_RESULT))
                ,cursor.getString(cursor.getColumnIndex(COLUMN_LAST_GUESS))
                ,cursor.getString(cursor.getColumnIndex(COLUMN_SECRET))
                ,cursor.getInt(cursor.getColumnIndex(COLUMN_GUESSES)))

            games.add(game)

        }
        cursor.close()
        return games
    }
}