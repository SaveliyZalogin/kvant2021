package com.arbonik.project

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vseved3.JSONHelper
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_meme.*


class MemeActivity : AppCompatActivity() {
    var memes: ArrayList<Meme> = ArrayList()
    var meme: Meme? = null
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)

        setSupportActionBar(toolbar)
        val id: Int = intent.getIntExtra("id", 0)
        val title: String? = intent.getStringExtra("title")
        val description: String? = intent.getStringExtra("description")
        val url: String? = intent.getStringExtra("url")
        meme = Meme(id, title, description, url)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "MEME"
        MainActivity().parse(meme_image, meme?.url)
        meme_title.text = meme?.title
        try {
            val favourite_list = JSONHelper.importFromJSON(this)
            if (favourite_list?.contains(meme)!!) {
                val drawable: Drawable? = resources.getDrawable(R.drawable.ic_baseline_star_24)
                drawable?.setBounds(0, 0, 50, 50)
                izbrannoe_button.setCompoundDrawables(drawable, null, null, null)
                izbrannoe_button.text = "В избранном"
            } else {
                val drawable: Drawable? = resources.getDrawable(R.drawable.ic_baseline_star_outline_24)
                drawable?.setBounds(0, 0, 50, 50)
                izbrannoe_button.setCompoundDrawables(drawable, null, null, null)
            }
        } catch (e: Exception) {
            e.stackTrace
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
        izbrannoe_button.setOnClickListener(object : View.OnClickListener {
            var isClicked = false
            override fun onClick(v: View?) {
                if (!isClicked) {
                    val drawable: Drawable? = resources.getDrawable(R.drawable.ic_baseline_star_24)
                    drawable?.setBounds(0, 0, 50, 50)
                    izbrannoe_button.setCompoundDrawables(drawable, null, null, null)
                    izbrannoe_button.text = "В избранном"
                    try {
                        val asd = JSONHelper.importFromJSON(applicationContext)
                        for (memem in asd!!) {
                            memes.add(memem!!)
                        }
                        memes.add(meme!!)
                        JSONHelper.exportToJSON(applicationContext, memes)
                        isClicked = true
                        Log.d("asd", "ff")
                    } catch (e: Exception) {
                        Log.d("asd", "gg")
                        memes.add(meme!!)
                        JSONHelper.exportToJSON(applicationContext, memes)
                        isClicked = true
                    }
                } else {
                    memes.clear()
                    val drawable: Drawable? = resources.getDrawable(R.drawable.ic_baseline_star_outline_24)
                    drawable?.setBounds(0, 0, 50, 50)
                    izbrannoe_button.setCompoundDrawables(drawable, null, null, null)
                    try {
                        val asd = JSONHelper.importFromJSON(applicationContext)
                        for (memem in asd!!) {
                            if (memem != meme) {
                                memes.add(memem!!)
                            }
                        }
                        JSONHelper.exportToJSON(applicationContext, memes)
                        isClicked = false
                        Log.d("asd", "ff")
                    } catch (e: Exception) {
                        Log.d("asd", "gg")
                    }
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
                shareIntent.putExtra(Intent.EXTRA_TEXT, meme?.url)
                shareIntent.type = "text/plain"
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}



