package com.arbonik.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.Adapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recyclerview_item.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = RecAdapter()
    }
    fun parse(image: ImageView, pos: Int) {
        val imageview: ImageView

        Thread(Runnable {
            val stringBuilder = StringBuilder()
            val doc: Document = Jsoup.connect("https://www.meme-arsenal.com/create/chose?type=picture&sort=latest").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)").get()
            try {
                val src: String = doc.select("img.mat-card-image.ng-star-inserted").get(pos).attr("src")
//                var secondEl = src.get(3)
                stringBuilder.append(src)
                Log.d("privet", stringBuilder.toString())
            } catch (e: IOException) {
                stringBuilder.append("Error : ").append(e.message).append("\n")
            }
            runOnUiThread { Picasso.get().load(stringBuilder.toString()).into(image) }
        }).start()
    }
}
