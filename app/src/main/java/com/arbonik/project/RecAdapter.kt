package com.arbonik.project

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecAdapter(width: Double, height: Double, search_query: String?) :
    RecyclerView.Adapter<RecAdapter.MyViewHolder>() {
    var width = width
    var height = height
    var counter = 0
    var topSetted = false
    var search_query = search_query
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        if (topSetted) {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
            return MyViewHolder(itemView, topSetted)
        } else {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.top_recycle_layout, parent, false)
            return MyViewHolder(itemView, topSetted)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (topSetted) {
            if (holder.image1?.isEnabled!!) {
                holder.image_left?.layoutParams?.width = width.toInt()
                holder.image_left?.layoutParams?.height = height.toInt()
                holder.image1?.let { MainActivity().parse(it, position + counter, search_query) }
                holder.card_left?.visibility = VISIBLE
                holder.image1?.isEnabled = false
                counter++
            }
            if (holder.image2?.isEnabled!!) {
                holder.image_right?.layoutParams?.width = width.toInt()
                holder.image_right?.layoutParams?.height = height.toInt()
                holder.image2?.let { MainActivity().parse(it, position + counter, search_query) }
                holder.image2?.isEnabled = false
            }
        } else {
            topSetted = true
        }
    }

    class MyViewHolder(itemView: View, topSetted: Boolean) : RecyclerView.ViewHolder(itemView) {
        var image1: ImageView? = null
        var image2: ImageView? = null
        var image_right: LinearLayout? = null
        var image_left: LinearLayout? = null
        var card_left: CardView? = null
        var card_right: CardView? = null
        init {
            if (topSetted) {
                image1 = itemView?.findViewById(R.id.image)
                image2 = itemView?.findViewById(R.id.image2)
                image_right = itemView?.findViewById(R.id.image_right)
                image_left = itemView?.findViewById(R.id.image_left)
                card_left = itemView?.findViewById(R.id.card_left)
                card_right = itemView?.findViewById(R.id.card_right)
            }
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}