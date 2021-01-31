package com.arbonik.project

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView

class RecAdapter(context: Context, width: Double, height: Double, urls: ArrayList<Meme>) :
    RecyclerView.Adapter<RecAdapter.MyViewHolder>() {
    var width = width
    var height = height
    var context = context
    var urls = urls
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val url = urls.get(position).url
        Log.d("pos", position.toString())
        holder.image_left?.layoutParams?.width = width.toInt()
        holder.image_left?.layoutParams?.height = height.toInt()
        holder.image1?.let { MainActivity().parse(it, url) }
        holder.card_left?.visibility = VISIBLE
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MemeActivity::class.java)
                intent.putExtra("url", url)
                context.startActivity(intent)
            }
        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image1: ImageView? = null
        var image_left: LinearLayout? = null
        var card_left: CardView? = null
        init {
            image1 = itemView.findViewById(R.id.image)
            image_left = itemView.findViewById(R.id.image_left)
            card_left = itemView.findViewById(R.id.card_left)
        }
    }

    override fun getItemCount(): Int {
        return urls.size
    }
}