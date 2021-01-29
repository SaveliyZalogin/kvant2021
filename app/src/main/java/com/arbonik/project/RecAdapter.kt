package com.arbonik.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recyclerview_item.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.net.HttpCookie.parse
import java.net.URI
import java.util.logging.Level.parse

class RecAdapter() :
    RecyclerView.Adapter<RecAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (holder.image1?.isEnabled!!) {
            holder.image1?.let { MainActivity().parse(it, position) }
            holder.image1?.isEnabled = false
        }
        if (holder.image2?.isEnabled!!) {
            holder.image2?.let { MainActivity().parse(it, position + 1) }
            holder.image2?.isEnabled = false
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image1: ImageView? = null
        var image2: ImageView? = null
        init {
            image1 = itemView?.findViewById(R.id.image)
            image2 = itemView?.findViewById(R.id.image2)
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}