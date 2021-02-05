package com.arbonik.project

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var search_query: String = ""
    var width: Int = 0
    var height: Int = 0
    val memes: ArrayList<Meme> = ArrayList()
    var counter: Int = 1
    var elems: Int = 1
    lateinit var settingsFragment: SettingsFragment
    lateinit var mainFragment: MainFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y

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

        nav_view.setNavigationItemSelectedListener(this)

        val mainFragment: MainFragment
        mainFragment = MainFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_viewer, mainFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        mainFragment.width = width


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

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.nav_home -> {
                mainFragment = MainFragment()
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_viewer, mainFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
            }
            R.id.nav_settings -> {
                settingsFragment = SettingsFragment()
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_viewer, settingsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
