package com.arbonik.project

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.vseved3.JSONHelper

class RecAdapter(context: Context?, width: Double, height: Double, urls: ArrayList<Meme>) :
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
        val meme = urls.get(position)
        val url = meme.url

        holder.image1?.layoutParams?.width = width.toInt()
        holder.image1?.layoutParams?.height = height.toInt()
        holder.image1?.let { MainActivity().parse(it, url) }
        holder.meme_text?.text = meme.title!!.split(",")[0]
        try {
            val favourite_memes = JSONHelper.importFromJSON(context)
            if (favourite_memes?.contains(meme)!!) {
                holder.izbrannoe_button?.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                holder.izbrannoe_button?.setImageResource(R.drawable.ic_baseline_star_outline_24)
            }
        } catch (e: Exception) {
            holder.izbrannoe_button?.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, MemeActivity::class.java)
                intent.putExtra("id", meme.id)
                intent.putExtra("title", meme.title)
                intent.putExtra("description", meme.description)
                intent.putExtra("url", meme.url)
                context?.startActivity(intent)
            }
        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image1: ImageView? = null
        var card_left: CardView? = null
        var meme_text: TextView? = null
        var izbrannoe_button: ImageButton? = null
        init {
            image1 = itemView.findViewById(R.id.image)
            card_left = itemView.findViewById(R.id.card_left)
            meme_text = itemView.findViewById(R.id.meme_text)
            izbrannoe_button = itemView.findViewById(R.id.izbrannoe_recycler)
        }
    }

    override fun getItemCount(): Int {
        return urls.size
    }
}

