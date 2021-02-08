package com.arbonik.project

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.GravityCompat
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.Exception


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    var search_query: String = ""
    var width: Int = 0
    var height: Int = 0
    val mainFragment = MainFragment()
    val favouriteFragment = FavouriteFragment()
    var prefs: SharedPreferences? = null
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            if (prefs?.getInt("theme", 0) == AppCompatDelegate.MODE_NIGHT_NO) {
                setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.navigationBarColor = android.R.color.transparent
            }
            else if (prefs?.getInt("theme", 0) == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        } catch (e: Exception) {
            setTheme(AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y

        nav_view.setOnNavigationItemSelectedListener(this)
        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_viewer, mainFragment).commit()
        mainFragment.width = width
        mainFragment.toolbar = supportActionBar

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val mainFragment = MainFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_viewer, mainFragment).commit()
                mainFragment.width = width
                supportActionBar?.show()
            }
            R.id.nav_izbrannoe -> {
                val favouriteFragment = FavouriteFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_viewer, favouriteFragment).commit()
                favouriteFragment.width = width
                supportActionBar?.show()
            }
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_toolbar -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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

            return retrofit.create(MemeApiService::class.java)
        }
    }
}
