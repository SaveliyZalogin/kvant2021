package com.arbonik.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
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
        val list = arrayListOf<String>()
        list.add("asdasdasd")
        list.add("asdasdasds")
        list.add("asdasdasdd")
        list.add("asdasdasdf")
        list.add("asdasdasdg")
        list.add("asdasdasdj")

        recyclerView.adapter = RecAdapter(list)
    }
    fun parse(image: ImageView, pos: Int) {
        Thread(Runnable {
            val stringBuilder = StringBuilder()
            try {
                val doc: Document = Jsoup.connect("https://joborgame.ru/game-lol").get()
                val src: String = doc.select("img.item-ico").get(pos).attr("src")
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
