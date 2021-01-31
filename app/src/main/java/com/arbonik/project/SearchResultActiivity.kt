package com.arbonik.project

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AbsListView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_search_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultActiivity : AppCompatActivity() {
    var search_query: String = ""
    var width: Int = 0
    var height: Int = 0
    val memes: ArrayList<String> = ArrayList()
    var elems: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y


        search_query = intent.getStringExtra("search_query").toString()
        supportActionBar!!.title = "Результат поиска: $search_query"
        val apiService = MemeApiService.create()
        val context = this
        val results = apiService.search_with_query(1, 30, search_query, "popular", "all", "ru")
        results.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>?, response: Response<Data>?) {
                for (meme in response!!.body().memes) {
                    Log.d("mypopa", meme.url!!)
                    memes.add(meme.url)
                }
                recycler_view.adapter = RecAdapter(context, width / 2.2, width / 2.2, elems,  memes)
            }
            override fun onFailure(call: Call<Data>?, t: Throwable?) {
                t!!.printStackTrace()
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}