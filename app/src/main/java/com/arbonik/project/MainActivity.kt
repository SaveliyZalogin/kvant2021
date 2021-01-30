package com.arbonik.project

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var search_query: String? = null
    var width: Int = 0
    var height: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        var display = windowManager.defaultDisplay
        var size = Point()
        display.getSize(size)
        width = size.x
        height = size.y

        recyclerView.adapter = RecAdapter(width / 2.2, width / 2.2, search_query)

        if (Intent.ACTION_SEARCH == intent.action) {
            Toast.makeText(applicationContext, "asd", Toast.LENGTH_LONG).show()
            search_query = intent.getStringExtra(SearchManager.QUERY)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "Результаты поиска: $search_query"
            recyclerView.adapter = RecAdapter(width / 2.2, width / 2.2, search_query)
            recyclerView.invalidate()
        }
    }
    fun parse(image: ImageView, pos: Int, search_query: String?) {
        Thread(Runnable {
            val doc: Document
            val stringBuilder = StringBuilder()
            if (search_query != null) {
                doc = Jsoup.connect("https://www.meme-arsenal.com/create/chose?type=picture&sort=latest&tag=$search_query")
                                .userAgent(
                                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)"
                                ).get()
            } else {
                print("asdf")
                doc = Jsoup.connect("https://www.meme-arsenal.com/create/chose?type=picture&sort=latest&")
                                .userAgent(
                                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)"
                                ).get()
            }
            try {
                val src: String = doc.select("img.mat-card-image.ng-star-inserted").get(pos).attr("src")
                stringBuilder.append(src)
                Log.d("privet", stringBuilder.toString())
            } catch (e: IOException) {
                stringBuilder.append("Error : ").append(e.message).append("\n")
            }
            runOnUiThread { Picasso.get().load(stringBuilder.toString()).into(image) }
        }).start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(true)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        supportActionBar!!.title = "MEME FINDER"
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        search_query = null
        recyclerView.adapter = RecAdapter(width / 2.2, width / 2.2, search_query)
        recyclerView.invalidate()
        return true
    }
}
