package com.sandbox.mastermind

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lost_history_row.view.*
import kotlinx.android.synthetic.main.mastermind_results.view.*
import kotlinx.android.synthetic.main.winner_history_row.view.*

class HistoryAdapter(val context: Context, val items: ArrayList<HistoryData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_LOST = 1
    private val TYPE_WIN =2

    class WinViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val tvResult = view.tvWinResult
        val img = view.iv_trophie

        fun setDetails(game: HistoryData){

            img.setImageResource(R.drawable.ic_trophy_small)
            tvResult.text = game.secret
        }
    }

    class LostViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvResult = view.tvLostResult
        val img = view.iv_sad

        fun setDetails(game: HistoryData){
            img.setImageResource(R.drawable.ic_sad_emoji_small)
            tvResult.text = game.secret

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType === TYPE_WIN){
            return WinViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.winner_history_row, parent, false
                )
            )
        } else {
            return LostViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.lost_history_row, parent, false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val game: HistoryData = items.get(position)
        Log.i("game", game.secret)

        if(getItemViewType(position) === TYPE_WIN){

            (holder as WinViewHolder).setDetails(game)
        } else {
            val LostHolder = holder as LostViewHolder
           LostHolder.setDetails(game)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {

        Log.i("ViewHolder", items.get(position).toString())
        var isWinner = items.get(position).result === 1


        if(isWinner){
            return TYPE_WIN
        } else {
            return TYPE_LOST
        }

    }

}