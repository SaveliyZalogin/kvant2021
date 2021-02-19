package com.arbonik.project

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.vseved3.JSONHelper
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_meme.*


class MemeActivity : AppCompatActivity() {
    var memes: ArrayList<Meme> = ArrayList()
    lateinit var meme: Meme
    var width: Int = 0
    var height: Int = 0
    var prefs: SharedPreferences? = null
    lateinit var favourite_list: List<Meme?>
    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y

        try {
            prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            if (prefs?.getInt("theme", 0) == AppCompatDelegate.MODE_NIGHT_NO) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        } catch (e: java.lang.Exception) {
        }
        setSupportActionBar(toolbar)

        val id: Int = intent.getIntExtra("id", 0)
        val title: String? = intent.getStringExtra("title")
        val description: String? = intent.getStringExtra("description")
        val url: String? = intent.getStringExtra("url")

        meme = Meme(id, title, description, url)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "MEME"

        MainActivity().parse(meme_image, meme.url)
        val k = meme_image.layoutParams.width / meme_image.layoutParams.height
        meme_image.layoutParams.width = (width / 1.1).toInt()
        meme_image.layoutParams.height = meme_image.layoutParams.width / k
        meme_title.text = meme.title
        try {
            favourite_list = JSONHelper.importFromJSON(this)!!
        } catch (e: Exception) {}

        val click_listener_add_to_favourite = object : View.OnClickListener {
            override fun onClick(v: View?) {
                try {
                    if (!favourite_list.contains(meme)) {
                        for (meme1 in favourite_list) {
                            memes.add(meme1!!)
                        }
                        memes.add(meme)
                        JSONHelper.exportToJSON(applicationContext, memes)
                        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.izbrannoe_anim)
                        animation.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {
                                back_layer.layoutParams.width = height
                                back_layer.visibility = VISIBLE
                            }
                            override fun onAnimationRepeat(animation: Animation?) {
                            }
                            override fun onAnimationEnd(animation: Animation?) {
                                back_layer.visibility = GONE
                                recreate()
                            }
                        })
                        back_layer.startAnimation(animation)
                    }
                } catch (e: Exception) {
                    memes.add(meme)
                    JSONHelper.exportToJSON(applicationContext, memes)
                }
            }
        }

        val click_listener_remove_from_favourite = object : View.OnClickListener {
            override fun onClick(v: View?) {
                for (meme1 in favourite_list) {
                    memes.add(meme1!!)
                }
                memes.remove(meme)
                JSONHelper.exportToJSON(applicationContext, memes)
                val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.izbrannoe_anim2)
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        izbrannoe_button.setBackgroundColor(android.R.color.transparent)
                        back_layer.layoutParams.width = height
                        back_layer.visibility = VISIBLE
                    }
                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                    override fun onAnimationEnd(animation: Animation?) {
                        back_layer.visibility = GONE
                        recreate()
                    }
                })
                back_layer.startAnimation(animation)
            }
        }

        if (favourite_list.contains(meme)) {
            izbrannoe_button.text = "В избранном"
            val color = resources.getColor(R.color.izbrannoe)
            izbrannoe_button.setBackgroundColor(color)
            izbrannoe_button.setOnClickListener(click_listener_remove_from_favourite)
        } else {
            izbrannoe_button.setOnClickListener(click_listener_add_to_favourite)
        }
        save_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                meme_image.setDrawingCacheEnabled(true)
                val b: Bitmap = meme_image.getDrawingCache()
                if (MediaStore.Images.Media.insertImage(contentResolver, b, "title", "desc") != null) {
                    Toast.makeText(applicationContext, "Сохранено", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu_meme, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MEME FROM MEME FINDER")
                shareIntent.putExtra(Intent.EXTRA_TEXT, meme.url)
                shareIntent.type = "text/plain"
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}



