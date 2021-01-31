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
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class MainActivity : AppCompatActivity() {
    var search_query: String = ""
    var width: Int = 0
    var height: Int = 0
    val memes: ArrayList<String> = ArrayList()
    var counter: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y

        load_data(0)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        if (Intent.ACTION_SEARCH == intent.action) {
            Toast.makeText(applicationContext, "asd", Toast.LENGTH_LONG).show()
            search_query = intent.getStringExtra(SearchManager.QUERY).toString()
            val intent = Intent(this, SearchResultActiivity::class.java)
            intent.putExtra("search_query", search_query)
            startActivity(intent)
        }
    }
    fun parse(image: ImageView, url: String?) {
        runOnUiThread { Picasso.get().load(url).into(image) }
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
    fun load_data(elems: Int) {
        val apiService = MemeApiService.create()
        val results = apiService.search(1, 30, "popular", "all", "ru")
        val context = this
        results.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>?, response: Response<Data>?) {
                for (meme in response!!.body().memes) {
                    Log.d("mypopa", meme.url!!)
                    memes.add(meme.url)
                }
                recyclerView.adapter = RecAdapter(context, width / 2.2, width / 2.2, elems, memes)
            }
            override fun onFailure(call: Call<Data>?, t: Throwable?) {
                t!!.printStackTrace()
            }
        })
    }
}
interface MemeApiService {

    @GET("api/templates-share")
    fun search(@Query("page") page: Int,
               @Query("items_on_page") items: Int,
               @Query("sort") sort: String,
               @Query("type") type: String,
               @Query("lang") lang: String): Call<Data>
    @GET("api/templates-share")
    fun search_with_query(@Query("page") page: Int,
               @Query("items_on_page") items: Int,
               @Query("query") query: String,
               @Query("sort") sort: String,
               @Query("type") type: String,
               @Query("lang") lang: String): Call<Data>

    companion object Factory {
        fun create(): MemeApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.meme-arsenal.ru/")
                    .build()

            return retrofit.create(MemeApiService::class.java);
        }
    }
}
