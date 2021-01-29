package com.arbonik.project

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var display = windowManager.defaultDisplay
        var size = Point()
        display.getSize(size)
        var width: Int = size.x
        var height: Int = size.y

        recyclerView.adapter = RecAdapter(width / 2.2, width / 2.2)

    }
    fun parse(image: ImageView, pos: Int) {
        Thread(Runnable {
            val stringBuilder = StringBuilder()
            val doc: Document =
                Jsoup.connect("https://www.meme-arsenal.com/create/chose?type=picture&sort=latest")
                    .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)"
                    ).get()
            try {
                val src: String =
                    doc.select("img.mat-card-image.ng-star-inserted").get(pos).attr("src")
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
