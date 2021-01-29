package com.arbonik.project

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecAdapter(width: Double, height: Double) :
    RecyclerView.Adapter<RecAdapter.MyViewHolder>() {
    var width = width
    var height = height
    var counter: Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        for (i in counter..10 step 2) {
            if (holder.image1?.isEnabled!!) {
                holder.image_left?.layoutParams?.width = width.toInt()
                holder.image_left?.layoutParams?.height = height.toInt()
                holder.image1?.let { MainActivity().parse(it, i - 1 ) }
                holder.card_left?.visibility = VISIBLE
                holder.image1?.isEnabled = false
            }
            if (holder.image2?.isEnabled!!) {
                holder.image_right?.layoutParams?.width = width.toInt()
                holder.image_right?.layoutParams?.height = height.toInt()
                holder.image2?.let { MainActivity().parse(it, i) }
                holder.image2?.isEnabled = false
            }
        }
        counter += 2
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image1: ImageView? = null
        var image2: ImageView? = null
        var image_right: LinearLayout? = null
        var image_left: LinearLayout? = null
        var card_left: CardView? = null
        var card_right: CardView? = null
        init {
            image1 = itemView?.findViewById(R.id.image)
            image2 = itemView?.findViewById(R.id.image2)
            image_right = itemView?.findViewById(R.id.image_right)
            image_left = itemView?.findViewById(R.id.image_left)
            card_left = itemView?.findViewById(R.id.card_left)
            card_right = itemView?.findViewById(R.id.card_right)
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}