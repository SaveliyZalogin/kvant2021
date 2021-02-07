package com.arbonik.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TopAdapter : RecyclerView.Adapter<TopAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.top_recycle_layout, parent, false)
        return TopAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 2
    }


}