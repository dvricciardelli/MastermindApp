package com.sandbox.mastermind

import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.guess_row.view.*

class ItemAdapter(val context: Context, val items: ArrayList<String>)
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val btnItem = view.btnLetter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.guess_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)

        holder.btnItem.text = item
    }

    override fun getItemCount(): Int {
        return items.size
    }


}